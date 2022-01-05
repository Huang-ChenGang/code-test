package com.cognizant.code.test.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductQueryResponseData {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal tax;

    private Integer quantity;
}
