package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.application.dto.ProductTotalPriceDto;
import com.cognizant.code.test.domain.model.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Page<Product> findAll(int pageNo, int pageSize);

    Product findById(String id);

    BigDecimal calculateTotalPrice(List<ProductTotalPriceDto> requestDtoList);
}
