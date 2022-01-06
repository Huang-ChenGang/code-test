package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;

public interface CartApplicationService {

    void addCartItem(CartItemAddRequestData requestData);

    void updateCartItem(CartItemUpdateRequestData requestData);
}
