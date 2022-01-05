package com.cognizant.code.test.infrastructure.handler;

import com.cognizant.code.test.infrastructure.api.ServerResponse;
import com.cognizant.code.test.infrastructure.exception.CodeTestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CodeTestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ServerResponse<Void> handleBadRequest(RuntimeException e) {
        log.error("bad request exception occurs", e);
        return ServerResponse.error(e);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CodeTestException.class)
    public ServerResponse<Void> handleCodeTestException(RuntimeException e) {
        log.error("code test exception occurs", e);
        return ServerResponse.error(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ServerResponse<Void> handleRuntimeException(RuntimeException e) {
        log.error("runtime exception occurs", e);
        return ServerResponse.error(e);
    }
}
