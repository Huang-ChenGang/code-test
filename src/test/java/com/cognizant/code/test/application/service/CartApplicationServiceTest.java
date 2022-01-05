package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.domain.model.Product;
import com.cognizant.code.test.domain.repository.CartProductRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import com.cognizant.code.test.domain.repository.ProductRepository;
import com.cognizant.code.test.infrastructure.handler.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CartApplicationServiceTest {

    @Autowired
    private CartApplicationService service;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private ProductRepository productRepository;

    private final String customerId = "123456";

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll();
        cartProductRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void saveCartProduct() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        CartProductSaveRequestData requestData = new CartProductSaveRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductId(product.getId());
        requestData.setQuantity(1);
        service.saveCartProduct(requestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(1, cartProductRepository.findAll().size());
    }

    @Test
    void testSaveProductNotExist() {
        CartProductSaveRequestData requestData = new CartProductSaveRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductId("12459");
        requestData.setQuantity(1);

        assertThrows(ProductNotFoundException.class, () -> service.saveCartProduct(requestData));
    }

    @Test
    void testIllegalQuantity() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        CartProductSaveRequestData requestData = new CartProductSaveRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductId(product.getId());
        requestData.setQuantity(200);

        assertThrows(IllegalArgumentException.class, () -> service.saveCartProduct(requestData));
    }
}