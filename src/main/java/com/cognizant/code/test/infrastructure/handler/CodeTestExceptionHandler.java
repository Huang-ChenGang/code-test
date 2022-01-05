package com.cognizant.code.test.infrastructure.handler;

import com.cognizant.code.test.infrastructure.api.ServerResponse;
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
    public ServerResponse<Void> handleBadRequest(Exception e) {
        log.error("bad request exception occurs", e);
        return ServerResponse.error(e);
    }
}
