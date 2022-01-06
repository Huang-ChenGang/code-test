package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemDeleteRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;
import com.cognizant.code.test.domain.model.Cart;
import com.cognizant.code.test.domain.model.CartItem;
import com.cognizant.code.test.domain.repository.CartItemRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import com.cognizant.code.test.infrastructure.exception.CartItemNotFoundException;
import com.cognizant.code.test.infrastructure.exception.CodeTestForbiddenException;
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

    @Transactional
    @Override
    public void updateCartItem(CartItemUpdateRequestData requestData) {
        Optional<Cart> cartOptional = cartRepository.findByCustomerId(requestData.getCustomerId());
        if (cartOptional.isEmpty()) {
            throw new CodeTestForbiddenException();
        }

        Cart cart = cartOptional.get();
        CartItem cartItem = findCartItem(requestData.getCartItemId());
        if (!cart.getId().equals(cartItem.getCartId())) {
            throw new CodeTestForbiddenException();
        }

        cartItem.setQuantity(requestData.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem findCartItem(String cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(CartItemNotFoundException::new);
    }

    @Transactional
    @Override
    public void deleteCartItem(CartItemDeleteRequestData requestData) {
        Optional<Cart> cartOptional = cartRepository.findByCustomerId(requestData.getCustomerId());
        if (cartOptional.isEmpty()) {
            throw new CodeTestForbiddenException();
        }

        Cart cart = cartOptional.get();
        requestData.getCartItemIdList().stream()
                .filter(cId -> cartItemRepository.findById(cId).isPresent())
                .peek(cId -> {
                    CartItem cartItem = findCartItem(cId);
                    if (!cart.getId().equals(cartItem.getCartId())) {
                        throw new CodeTestForbiddenException();
                    }
                })
                .forEach(cartItemRepository::deleteById);
    }

}
