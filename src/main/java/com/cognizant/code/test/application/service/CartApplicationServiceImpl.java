package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemAddRequestData.CartItemAddData;
import com.cognizant.code.test.api.CartItemDeleteRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;
import com.cognizant.code.test.application.dto.ProductDto;
import com.cognizant.code.test.domain.model.CartItem;
import com.cognizant.code.test.domain.service.CartService;
import com.cognizant.code.test.infrastructure.exception.CodeTestException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartApplicationServiceImpl implements CartApplicationService {

    private final CartService cartService;
    private final ProductApplicationService productApplicationService;

    public CartApplicationServiceImpl(CartService cartService,
                                      ProductApplicationService productApplicationService) {
        this.cartService = cartService;
        this.productApplicationService = productApplicationService;
    }

    @Override
    public void addCartItem(CartItemAddRequestData requestData) {
        // fix repeated product
        requestData.setProductList(
                requestData.getProductList().stream()
                        .collect(Collectors
                                .toMap(CartItemAddData::getProductId, CartItemAddData::getQuantity, Integer::sum))
                        .entrySet().stream()
                        .map(e -> new CartItemAddData(e.getKey(), e.getValue()))
                        .collect(Collectors.toList()));

        requestData.getProductList().forEach(p -> {
            int productQuantity = cartService.findProductQuantity(requestData.getCustomerId(), p.getProductId());
            ProductDto product = productApplicationService.findById(p.getProductId());
            if (productQuantity + p.getQuantity() > product.getQuantity()) {
                throw new CodeTestException("illegal quantity");
            }
        });

        cartService.addCartItem(requestData);
    }

    @Override
    public void updateCartItem(CartItemUpdateRequestData requestData) {
        CartItem cartItem = cartService.findCartItem(requestData.getCartItemId());
        ProductDto product = productApplicationService.findById(cartItem.getProductId());
        if (requestData.getQuantity() > product.getQuantity()) {
            throw new CodeTestException("illegal quantity");
        }

        cartService.updateCartItem(requestData);
    }

    @Override
    public void deleteCartItem(CartItemDeleteRequestData requestData) {
        cartService.deleteCartItem(requestData);
    }
}
