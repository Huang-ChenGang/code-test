package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;
import com.cognizant.code.test.domain.model.CartItem;

public interface CartService {

    void addCartItem(CartItemAddRequestData requestData);

    int findProductQuantity(String customerId, String productId);

    void updateCartItem(CartItemUpdateRequestData requestData);

    CartItem findCartItem(String cartItemId);
}
