package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.application.dto.ProductDto;
import com.cognizant.code.test.domain.service.CartService;
import com.cognizant.code.test.infrastructure.exception.CodeTestException;
import org.springframework.stereotype.Service;

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
    public void saveCartProduct(CartProductSaveRequestData requestData) {
        ProductDto product = productApplicationService.findById(requestData.getProductId());
        if (requestData.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("illegal quantity");
        }

        cartService.saveCartProduct(requestData);
    }

    @Override
    public void addCartItem(CartItemAddRequestData requestData) {
        requestData.getProductList().forEach(p -> {
            int productQuantity = cartService.findProductQuantity(requestData.getCustomerId(), p.getProductId());
            ProductDto product = productApplicationService.findById(p.getProductId());
            if (productQuantity + p.getQuantity() > product.getQuantity()) {
                throw new CodeTestException("illegal quantity");
            }
        });

        cartService.addCartItem(requestData);
    }
}
