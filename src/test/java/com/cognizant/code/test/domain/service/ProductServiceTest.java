package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.application.dto.ProductTotalPriceDto;
import com.cognizant.code.test.domain.model.Product;
import com.cognizant.code.test.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

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

    @Test
    void findAll() {
        Page<Product> productPageOne = service.findAll(0, 10);
        assertEquals(35, productPageOne.getTotalElements());
        assertEquals(4, productPageOne.getTotalPages());
        assertEquals(10, productPageOne.getSize());
    }

    @Test
    void testTotalPrice() {
        Product product = new Product();
        product.setName("product test");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setTax(BigDecimal.TEN);
        product.setQuantity(200);
        product = repository.save(product);

        Product product2 = new Product();
        product2.setName("product test 2");
        product2.setPrice(BigDecimal.valueOf(1800));
        product2.setTax(BigDecimal.valueOf(15));
        product2.setQuantity(200);
        product2 = repository.save(product2);

        List<ProductTotalPriceDto> requestDtoList = new ArrayList<>();
        requestDtoList.add(new ProductTotalPriceDto(product.getId(), 20));
        requestDtoList.add(new ProductTotalPriceDto(product2.getId(), 10));
        assertEquals(BigDecimal.valueOf(42700), service.calculateTotalPrice(requestDtoList));
    }
}