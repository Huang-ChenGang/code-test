package com.cognizant.code.test.application.controller;

import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.application.service.CartApplicationService;
import com.cognizant.code.test.infrastructure.api.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartApplicationService service;

    public CartController(CartApplicationService service) {
        this.service = service;
    }

    @PostMapping("/product")
    public ServerResponse<Void> saveCartProduct(@RequestBody @Valid CartProductSaveRequestData requestData) {
        log.info("attempt to save cart product for request data: {}", requestData);
        service.saveCartProduct(requestData);
        return ServerResponse.success();
    }
}
