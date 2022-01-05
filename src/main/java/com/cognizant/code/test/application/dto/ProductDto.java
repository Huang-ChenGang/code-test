package com.cognizant.code.test.application.dto;

import com.cognizant.code.test.api.ProductQueryResponseData;
import com.cognizant.code.test.domain.model.Product;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal tax;

    private Integer quantity;

    public static ProductDto fromModel(Product model) {
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }

    public static ProductQueryResponseData toResponseData(ProductDto dto) {
        ProductQueryResponseData responseData = new ProductQueryResponseData();
        BeanUtils.copyProperties(dto, responseData);
        return responseData;
    }
}
