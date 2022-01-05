package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.application.dto.ProductDto;
import com.cognizant.code.test.domain.service.CartService;
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
}
