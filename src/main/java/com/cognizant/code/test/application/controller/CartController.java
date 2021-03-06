package com.cognizant.code.test.application.controller;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemDeleteRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;
import com.cognizant.code.test.application.service.CartApplicationService;
import com.cognizant.code.test.infrastructure.api.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Slf4j
@Validated
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartApplicationService service;

    public CartController(CartApplicationService service) {
        this.service = service;
    }

    @PostMapping("/products")
    public ServerResponse<Void> addCartProduct(@RequestBody @Valid CartItemAddRequestData requestData) {
        log.info("attempt to add products to cart for request data: {}", requestData);
        service.addCartItem(requestData);
        return ServerResponse.success();
    }

    @PutMapping("/product")
    public ServerResponse<Void> updateCartProduct(@RequestBody @Valid CartItemUpdateRequestData requestData) {
        log.info("attempt to update product to cart for request data: {}", requestData);
        service.updateCartItem(requestData);
        return ServerResponse.success();
    }

    @DeleteMapping("/products")
    public ServerResponse<Void> deleteCartProduct(@RequestBody @Valid CartItemDeleteRequestData requestData) {
        log.info("attempt to delete products from cart for request data: {}", requestData);
        service.deleteCartItem(requestData);
        return ServerResponse.success();
    }

    @GetMapping("/overall-bill/{customerId}")
    public ServerResponse<BigDecimal> getOverallBill(@PathVariable @NotBlank String customerId) {
        log.info("attempt to get overall bill for customerId: {}", customerId);
        return ServerResponse.success(service.getOverallBill(customerId));
    }
}
