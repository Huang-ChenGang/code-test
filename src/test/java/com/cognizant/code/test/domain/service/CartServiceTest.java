package com.cognizant.code.test.domain.service;

import com.cognizant.code.test.api.CartItemAddRequestData;
import com.cognizant.code.test.api.CartItemAddRequestData.CartItemAddData;
import com.cognizant.code.test.api.CartItemDeleteRequestData;
import com.cognizant.code.test.api.CartItemUpdateRequestData;
import com.cognizant.code.test.domain.model.Cart;
import com.cognizant.code.test.domain.model.CartItem;
import com.cognizant.code.test.domain.model.Product;
import com.cognizant.code.test.domain.repository.CartItemRepository;
import com.cognizant.code.test.domain.repository.CartRepository;
import com.cognizant.code.test.domain.repository.ProductRepository;
import com.cognizant.code.test.infrastructure.exception.CartItemNotFoundException;
import com.cognizant.code.test.infrastructure.exception.CodeTestForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testAddCartItemMultiple() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        Product product2 = new Product();
        product2.setName("test product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setTax(BigDecimal.TEN);
        product2.setQuantity(100);
        product2 = productRepository.save(product2);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);

        List<CartItemAddData> cartItemAddDataList = new ArrayList<>();
        cartItemAddDataList.add(new CartItemAddData(product.getId(), 1));
        cartItemAddDataList.add(new CartItemAddData(product2.getId(), 2));
        requestData.setProductList(cartItemAddDataList);
        service.addCartItem(requestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(2, cartItemRepository.findAll().size());
        assertEquals(1, cartItemRepository.findAll().get(0).getQuantity());
        assertEquals(2, cartItemRepository.findAll().get(1).getQuantity());
    }

    @Test
    void testFindProductQuantity() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        Product product2 = new Product();
        product2.setName("test product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setTax(BigDecimal.TEN);
        product2.setQuantity(100);
        product2 = productRepository.save(product2);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);

        List<CartItemAddData> cartItemAddDataList = new ArrayList<>();
        cartItemAddDataList.add(new CartItemAddData(product.getId(), 1));
        cartItemAddDataList.add(new CartItemAddData(product2.getId(), 2));
        requestData.setProductList(cartItemAddDataList);
        service.addCartItem(requestData);

        assertEquals(0, service.findProductQuantity("123", "456"));
        assertEquals(0, service.findProductQuantity(customerId, "456"));
        assertEquals(1, service.findProductQuantity(customerId, product.getId()));
        assertEquals(2, service.findProductQuantity(customerId, product2.getId()));
    }

    @Test
    void testUpdateCartItemNoCustomer() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        Product product2 = new Product();
        product2.setName("test product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setTax(BigDecimal.TEN);
        product2.setQuantity(100);
        product2 = productRepository.save(product2);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);

        List<CartItemAddData> cartItemAddDataList = new ArrayList<>();
        cartItemAddDataList.add(new CartItemAddData(product.getId(), 1));
        cartItemAddDataList.add(new CartItemAddData(product2.getId(), 2));
        requestData.setProductList(cartItemAddDataList);
        service.addCartItem(requestData);

        CartItemUpdateRequestData updateRequestData = new CartItemUpdateRequestData();
        updateRequestData.setCustomerId("908");
        updateRequestData.setCartItemId("887");
        updateRequestData.setQuantity(3);
        assertThrows(CodeTestForbiddenException.class, () -> service.updateCartItem(updateRequestData));
    }

    @Test
    void testUpdateCartItemNoCartItem() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        Product product2 = new Product();
        product2.setName("test product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setTax(BigDecimal.TEN);
        product2.setQuantity(100);
        product2 = productRepository.save(product2);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);

        List<CartItemAddData> cartItemAddDataList = new ArrayList<>();
        cartItemAddDataList.add(new CartItemAddData(product.getId(), 1));
        cartItemAddDataList.add(new CartItemAddData(product2.getId(), 2));
        requestData.setProductList(cartItemAddDataList);
        service.addCartItem(requestData);

        CartItemUpdateRequestData updateRequestData = new CartItemUpdateRequestData();
        updateRequestData.setCustomerId(customerId);
        updateRequestData.setCartItemId("887");
        updateRequestData.setQuantity(3);
        assertThrows(CartItemNotFoundException.class, () -> service.updateCartItem(updateRequestData));
    }

    @Test
    void testUpdateCartItemWrongCustomer() {
        String customer2 = "87653922";

        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        Product product2 = new Product();
        product2.setName("test product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setTax(BigDecimal.TEN);
        product2.setQuantity(100);
        product2 = productRepository.save(product2);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);
        requestData.setProductList(Collections.singletonList(new CartItemAddData(product.getId(), 1)));
        service.addCartItem(requestData);

        CartItemAddRequestData requestData2 = new CartItemAddRequestData();
        requestData2.setCustomerId(customer2);
        requestData2.setProductList(Collections.singletonList(new CartItemAddData(product2.getId(), 2)));
        service.addCartItem(requestData2);

        Cart customer2Cart = cartRepository.findByCustomerId(customer2).orElseThrow();
        CartItem customer2CartItem = cartItemRepository
                .findByCartIdAndProductId(customer2Cart.getId(), product2.getId()).orElseThrow();

        CartItemUpdateRequestData updateRequestData = new CartItemUpdateRequestData();
        updateRequestData.setCustomerId(customerId);
        updateRequestData.setCartItemId(customer2CartItem.getId());
        updateRequestData.setQuantity(3);
        assertThrows(CodeTestForbiddenException.class, () -> service.updateCartItem(updateRequestData));
    }

    @Test
    void testUpdateCartItem() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        Product product2 = new Product();
        product2.setName("test product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setTax(BigDecimal.TEN);
        product2.setQuantity(100);
        product2 = productRepository.save(product2);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);

        List<CartItemAddData> cartItemAddDataList = new ArrayList<>();
        cartItemAddDataList.add(new CartItemAddData(product.getId(), 1));
        cartItemAddDataList.add(new CartItemAddData(product2.getId(), 2));
        requestData.setProductList(cartItemAddDataList);
        service.addCartItem(requestData);

        Cart cart = cartRepository.findByCustomerId(customerId).orElseThrow();
        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product2.getId()).orElseThrow();

        assertEquals(2, cartItem.getQuantity());

        CartItemUpdateRequestData updateRequestData = new CartItemUpdateRequestData();
        updateRequestData.setCustomerId(customerId);
        updateRequestData.setCartItemId(cartItem.getId());
        updateRequestData.setQuantity(3);
        service.updateCartItem(updateRequestData);

        CartItem updatedItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product2.getId()).orElseThrow();

        assertEquals(3, updatedItem.getQuantity());
    }

    @Test
    void testDeleteCartItem() {
        Product product = new Product();
        product.setName("test product");
        product.setPrice(BigDecimal.TEN);
        product.setTax(BigDecimal.TEN);
        product.setQuantity(100);
        product = productRepository.save(product);

        Product product2 = new Product();
        product2.setName("test product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setTax(BigDecimal.TEN);
        product2.setQuantity(100);
        product2 = productRepository.save(product2);

        CartItemAddRequestData requestData = new CartItemAddRequestData();
        requestData.setCustomerId(customerId);

        List<CartItemAddData> cartItemAddDataList = new ArrayList<>();
        cartItemAddDataList.add(new CartItemAddData(product.getId(), 1));
        cartItemAddDataList.add(new CartItemAddData(product2.getId(), 2));
        requestData.setProductList(cartItemAddDataList);
        service.addCartItem(requestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(2, cartItemRepository.findAll().size());

        Cart cart = cartRepository.findByCustomerId(customerId).orElseThrow();
        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId()).orElseThrow();
        CartItem cartItem2 = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product2.getId()).orElseThrow();

        CartItemDeleteRequestData deleteRequestData = new CartItemDeleteRequestData();
        deleteRequestData.setCustomerId(customerId);
        deleteRequestData.setCartItemIdList(Arrays.asList(cartItem.getId(), cartItem2.getId()));
        service.deleteCartItem(deleteRequestData);

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(0, cartItemRepository.findAll().size());
    }

}