package com.cognizant.code.test.infrastructure.exception;

public class CodeTestException extends RuntimeException {

    public CodeTestException() {
        super("code test exception!");
    }

    public CodeTestException(String errorMessage) {
        super(errorMessage);
    }
}
