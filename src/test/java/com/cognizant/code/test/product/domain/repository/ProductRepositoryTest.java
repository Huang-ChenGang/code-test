package com.cognizant.code.test.product.domain.repository;

import com.cognizant.code.test.product.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testSave() {
        Product product = new Product();
        product.setName("test product");
        repository.save(product);
        assertEquals(1, repository.findAll().size());
    }
}