package com.cognizant.code.test.domain.repository;

import com.cognizant.code.test.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        repository.save(product);

        assertEquals(1, repository.findAll().size());
        assertNotNull(repository.findAll().get(0).getId());
        assertNotNull(repository.findAll().get(0).getCreateTime());
        assertNotNull(repository.findAll().get(0).getUpdateTime());
    }
}