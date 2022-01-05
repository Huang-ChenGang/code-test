package com.cognizant.code.test.application.dto;

import com.cognizant.code.test.api.ProductResponseData;
import com.cognizant.code.test.domain.model.Product;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ProductDto {

    private String id;

    private String name;

    public static ProductDto fromModel(Product model) {
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }

    public static ProductResponseData toResponseData(ProductDto dto) {
        ProductResponseData responseData = new ProductResponseData();
        BeanUtils.copyProperties(dto, responseData);
        return responseData;
    }
}
