package com.cognizant.code.test.infrastructure.handler;

import com.cognizant.code.test.infrastructure.exception.CodeTestException;

public class ProductNotFoundException extends CodeTestException {

    public ProductNotFoundException() {
        super("product not found!");
    }
}
