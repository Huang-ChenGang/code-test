package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.domain.model.Cart;
import com.cognizant.code.test.domain.model.CartProduct;
import com.cognizant.code.test.domain.repository.CartProductRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        CartProduct cartProduct = cartProductRepository.save(new CartProduct(
                cart.getId(), requestData.getProductId(), requestData.getQuantity()));
        if (cartProduct.getQuantity() == 0) {
            cartProductRepository.deleteById(cartProduct.getId());
        }
    }
}
