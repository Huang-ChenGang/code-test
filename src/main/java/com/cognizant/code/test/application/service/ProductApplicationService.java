package com.cognizant.code.test.application.service;

import com.cognizant.code.test.application.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductApplicationService {

    Page<ProductDto> findAll(int pageNo, int pageSize);

    int findQuantity(String id);
}
