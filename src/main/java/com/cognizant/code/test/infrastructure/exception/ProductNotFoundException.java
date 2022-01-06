package com.cognizant.code.test.infrastructure.exception;

public class ProductNotFoundException extends CodeTestException {

    public ProductNotFoundException() {
        super("product not found!");
    }
}
