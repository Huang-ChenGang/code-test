package com.cognizant.code.test.infrastructure.exception;

public class CartItemNotFoundException extends CodeTestException {

    public CartItemNotFoundException() {
        super("cart item not found!");
    }
}
