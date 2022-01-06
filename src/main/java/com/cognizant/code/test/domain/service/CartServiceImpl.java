package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.domain.model.Cart;
import com.cognizant.code.test.domain.model.CartItem;
import com.cognizant.code.test.domain.repository.CartItemRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    @Override
    public void saveCartProduct(CartProductSaveRequestData requestData) {
        Cart cart = cartRepository.findByCustomerId(requestData.getCustomerId())
                .orElseGet(() -> cartRepository.save(new Cart(requestData.getCustomerId())));

        Optional<CartItem> cartProductOptional = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), requestData.getProductId());
        if (cartProductOptional.isPresent()) {
            CartItem cartItem = cartProductOptional.get();
            log.info("update product for exist cart: {}", cartItem);
            cartItem.setQuantity(requestData.getQuantity());
            cartItemRepository.save(cartItem);
            return;
        }

        if (requestData.getQuantity() == 0) {
            return;
        }

        cartItemRepository.save(new CartItem(
                cart.getId(), requestData.getProductId(), requestData.getQuantity()));
    }

    @Override
    public void addCartItem(CartItemAddRequestData requestData) {
        Cart cart = cartRepository.findByCustomerId(requestData.getCustomerId())
                .orElseGet(() -> cartRepository.save(new Cart(requestData.getCustomerId())));

        requestData.getProductList().stream()
                .map(p -> {
                    Optional<CartItem> cartItemOptional = cartItemRepository
                            .findByCartIdAndProductId(cart.getId(), p.getProductId());
                    if (cartItemOptional.isPresent()) {
                        CartItem cartItem = cartItemOptional.get();
                        cartItem.setQuantity(cartItem.getQuantity() + p.getQuantity());
                        return cartItem;
                    }
                    return new CartItem(cart.getId(), p.getProductId(), p.getQuantity());
                })
                .forEach(cartItemRepository::save);

    }

    @Override
    public int findProductQuantity(String customerId, String productId) {
        Optional<Cart> cartOptional = cartRepository.findByCustomerId(customerId);
        if (cartOptional.isEmpty()) {
            log.info("no cart found for customerId: {}, productId: {}", customerId, productId);
            return 0;
        }

        Cart cart = cartOptional.get();
        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (cartItemOptional.isEmpty()) {
            log.info("no cart item found for customerId: {}, productId: {}", customerId, productId);
            return 0;
        }

        log.info("found product quantity: {} for customerId: {}, productId: {}",
                cartItemOptional.get().getQuantity(), customerId, productId);
        return cartItemOptional.get().getQuantity();
    }

}
