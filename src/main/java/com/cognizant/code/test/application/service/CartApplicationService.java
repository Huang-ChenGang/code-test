package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartProductSaveRequestData;

public interface CartApplicationService {

    void saveCartProduct(CartProductSaveRequestData requestData);

    void addCartItem(CartItemAddRequestData requestData);
}
