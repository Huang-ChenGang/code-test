package com.cognizant.code.test.infrastructure.exception;

public class CodeTestForbiddenException extends CodeTestException {

    public CodeTestForbiddenException() {
        super("forbidden!");
    }
}
