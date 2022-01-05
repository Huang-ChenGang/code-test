package com.cognizant.code.test.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductSaveRequestData {

    @NotBlank
    private String customerId;

    @NotBlank
    private String productId;

    @NotNull
    @PositiveOrZero
    private Integer quantity;
}
