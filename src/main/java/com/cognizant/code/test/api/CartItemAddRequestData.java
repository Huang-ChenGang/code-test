package com.cognizant.code.test.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@Data
public class CartItemAddRequestData {

    @NotBlank
    private String customerId;

    @NotEmpty
    private List<CartItemAddData> productList;

    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CartItemAddData {

        @NotBlank
        private String productId;

        @NotNull
        @Positive
        private Integer quantity;
    }
}
