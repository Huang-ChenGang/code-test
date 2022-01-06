package com.cognizant.code.test.application.service;

import com.cognizant.code.test.application.dto.ProductDto;
import com.cognizant.code.test.application.dto.ProductTotalPriceDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductApplicationService {

    Page<ProductDto> findAll(int pageNo, int pageSize);

    int findQuantity(String id);

    BigDecimal calculateTotalPrice(List<ProductTotalPriceDto> requestDtoList);
}
