package com.cognizant.code.test.application.service;

import com.cognizant.code.test.application.dto.ProductDto;
import com.cognizant.code.test.domain.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {
    private final ProductService service;

    public ProductApplicationServiceImpl(ProductService service) {
        this.service = service;
    }

    @Override
    public Page<ProductDto> findAll(int pageNo, int pageSize) {
        return service.findAll(pageNo, pageSize)
                .map(ProductDto::fromModel);
    }

    @Override
    public ProductDto findById(String id) {
        return ProductDto.fromModel(service.findById(id));
    }
}
