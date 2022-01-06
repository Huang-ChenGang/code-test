package com.cognizant.code.test.api;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
@Data
public class CartItemUpdateRequestData {

    @NotBlank
    private String customerId;

    @NotBlank
    private String cartItemId;

    @NotNull
    @Positive
    private Integer quantity;

}
