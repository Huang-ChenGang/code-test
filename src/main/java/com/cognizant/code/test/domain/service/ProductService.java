package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.domain.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<Product> findAll(int pageNo, int pageSize);
}
