package com.cognizant.code.test.api;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@Data
public class CartProductDeleteRequestData {

    @NotBlank
    private String customerId;

    @NotEmpty
    private List<String> cartProductIdList;

}
