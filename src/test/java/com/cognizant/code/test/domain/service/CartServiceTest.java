package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.api.CartProductSaveRequestData;
import com.cognizant.code.test.domain.model.Product;
import com.cognizant.code.test.domain.repository.CartItemRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import com.cognizant.code.test.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService service;

    private final String customerId = "123456";

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll();
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void save() {
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
        assertEquals(1, cartItemRepository.findAll().size());
    }

    @Test
    void saveZero() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        CartProductSaveRequestData requestData = new CartProductSaveRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductId(product.getId());
        requestData.setQuantity(0);
        service.saveCartProduct(requestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(0, cartItemRepository.findAll().size());
    }

}