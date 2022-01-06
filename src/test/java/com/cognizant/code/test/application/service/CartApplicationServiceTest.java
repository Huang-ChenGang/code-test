package com.cognizant.code.test.application.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemAddRequestData.CartItemAddData;
import com.cognizant.code.test.domain.model.Product;
import com.cognizant.code.test.domain.repository.CartItemRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import com.cognizant.code.test.domain.repository.ProductRepository;
import com.cognizant.code.test.infrastructure.exception.CodeTestException;
import com.cognizant.code.test.infrastructure.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CartApplicationServiceTest {

    @Autowired
    private CartApplicationService service;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    private final String customerId = "123456";

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll();
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void testAddCartItem() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductList(Collections.singletonList(new CartItemAddData(product.getId(), 1)));
        service.addCartItem(requestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(1, cartItemRepository.findAll().size());
    }

    @Test
    void testAddCartItemRepeatedProduct() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);

        List<CartItemAddData> productList = new ArrayList<>();
        productList.add(new CartItemAddData(product.getId(), 1));
        productList.add(new CartItemAddData(product.getId(), 20));
        requestData.setProductList(productList);

        service.addCartItem(requestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(1, cartItemRepository.findAll().size());
        assertEquals(21, cartItemRepository.findAll().get(0).getQuantity());
    }

    @Test
    void testAddCartItemProductNotExist() {
        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductList(Collections.singletonList(new CartItemAddData("2349", 1)));

        assertThrows(ProductNotFoundException.class, () -> service.addCartItem(requestData));
    }

    @Test
    void testIllegalQuantity() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductList(Collections.singletonList(new CartItemAddData(product.getId(), 200)));

        assertThrows(CodeTestException.class, () -> service.addCartItem(requestData));
    }

    @Test
    void testIllegalQuantityWhenExist() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductList(Collections.singletonList(new CartItemAddData(product.getId(), 90)));
        service.addCartItem(requestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(1, cartItemRepository.findAll().size());

        CartItemAddRequestData requestData2 = new CartItemAddRequestData();
        requestData2.setCustomerId(customerId);
        requestData2.setProductList(Collections.singletonList(new CartItemAddData(product.getId(), 50)));

        assertThrows(CodeTestException.class, () -> service.addCartItem(requestData));
    }
}