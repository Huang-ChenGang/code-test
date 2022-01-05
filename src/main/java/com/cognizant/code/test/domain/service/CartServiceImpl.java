package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.domain.model.Cart;
import com.cognizant.code.test.domain.model.CartProduct;
import com.cognizant.code.test.domain.repository.CartProductRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    @Override
    public void saveCartProduct(CartProductSaveRequestData requestData) {
        Cart cart = cartRepository.findByCustomerId(requestData.getCustomerId())
                .orElseGet(() -> cartRepository.save(new Cart(requestData.getCustomerId())));

        Optional<CartProduct> cartProductOptional = cartProductRepository
                .findByCartIdAndProductId(cart.getId(), requestData.getProductId());
        if (cartProductOptional.isPresent()) {
            CartProduct cartProduct = cartProductOptional.get();
            log.info("save for exist cart product: {}", cartProduct);
            cartProduct.setQuantity(requestData.getQuantity());
            cartProductRepository.save(cartProduct);
            return;
        }

        if (requestData.getQuantity() == 0) {
            return;
        }

        cartProductRepository.save(new CartProduct(
                cart.getId(), requestData.getProductId(), requestData.getQuantity()));
    }

}
