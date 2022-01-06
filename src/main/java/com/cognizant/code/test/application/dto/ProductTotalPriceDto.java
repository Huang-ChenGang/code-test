package com.cognizant.code.test.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductTotalPriceDto {

    @NotBlank
    private String productId;

    @NotNull
    @Positive
    private Integer quantity;

}
