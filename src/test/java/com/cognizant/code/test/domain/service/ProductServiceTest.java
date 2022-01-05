package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.domain.model.Product;
import com.cognizant.code.test.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}