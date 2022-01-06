package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.application.dto.ProductTotalPriceDto;
import com.cognizant.code.test.domain.model.Product;
import com.cognizant.code.test.domain.repository.ProductRepository;
import com.cognizant.code.test.infrastructure.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    // for test
    @PostConstruct
    private void init() {
        List<Product> initList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            Product product = new Product();
            product.setName("product " + i);
            product.setPrice(BigDecimal.TEN);
            product.setTax(BigDecimal.TEN);
            product.setQuantity(100);
            initList.add(product);
        }
        repository.saveAll(initList);
    }

    @Override
    public Page<Product> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    @Override
    public Product findById(String id) {
        return repository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public BigDecimal calculateTotalPrice(List<ProductTotalPriceDto> requestDtoList) {
        log.info("product service attempt to calculate total price for request: {}", requestDtoList);
        return requestDtoList.stream()
                .map(dto -> {
                    Product product = findById(dto.getProductId());
                    BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
                    BigDecimal tax = price.multiply(product.getTax())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    return price.add(tax).stripTrailingZeros();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
