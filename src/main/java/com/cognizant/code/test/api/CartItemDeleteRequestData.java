package com.cognizant.code.test.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CartItemDeleteRequestData {

    @NotBlank
    private String customerId;

    @NotEmpty
    private List<String> cartItemIdList;
}
