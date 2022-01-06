package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemDeleteRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;

import java.math.BigDecimal;

public interface CartApplicationService {

    void addCartItem(CartItemAddRequestData requestData);

    void updateCartItem(CartItemUpdateRequestData requestData);

    void deleteCartItem(CartItemDeleteRequestData requestData);

    BigDecimal getOverallBill(String customerId);
}
