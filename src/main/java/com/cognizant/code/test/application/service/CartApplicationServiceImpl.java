package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemAddRequestData.CartItemAddData;
import com.cognizant.code.test.api.CartItemDeleteRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;
import com.cognizant.code.test.application.dto.ProductTotalPriceDto;
import com.cognizant.code.test.domain.model.CartItem;
import com.cognizant.code.test.domain.service.CartService;
import com.cognizant.code.test.infrastructure.exception.CodeTestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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
            int cartProductQuantity = cartService.findProductQuantity(requestData.getCustomerId(), p.getProductId());
            int productQuantity = productApplicationService.findQuantity(p.getProductId());
            if (cartProductQuantity + p.getQuantity() > productQuantity) {
                throw new CodeTestException("illegal quantity");
            }
        });

        cartService.addCartItem(requestData);
    }

    @Override
    public void updateCartItem(CartItemUpdateRequestData requestData) {
        CartItem cartItem = cartService.findCartItem(requestData.getCartItemId());
        int productQuantity = productApplicationService.findQuantity(cartItem.getProductId());
        if (requestData.getQuantity() > productQuantity) {
            throw new CodeTestException("illegal quantity");
        }

        cartService.updateCartItem(requestData);
    }

    @Override
    public void deleteCartItem(CartItemDeleteRequestData requestData) {
        cartService.deleteCartItem(requestData);
    }

    @Override
    public BigDecimal getOverallBill(String customerId) {
        List<CartItem> cartItemList = cartService.findCartItemList(customerId);

        List<ProductTotalPriceDto> requestDtoList = cartItemList.stream()
                .map(c -> new ProductTotalPriceDto(c.getProductId(), c.getQuantity()))
                .collect(Collectors.toList());

        return productApplicationService.calculateTotalPrice(requestDtoList);
    }
}
