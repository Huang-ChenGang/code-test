package com.cognizant.code.test.application.controller;

import com.cognizant.code.test.api.ProductResponseData;
import com.cognizant.code.test.application.dto.ProductDto;
import com.cognizant.code.test.application.service.ProductApplicationService;
import com.cognizant.code.test.infrastructure.ServerResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductApplicationService service;

    public ProductController(ProductApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public ServerResponse<Page<ProductResponseData>> findAll(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ServerResponse.success(
                service.findAll(pageNo, pageSize)
                        .map(ProductDto::toResponseData));
    }
}
