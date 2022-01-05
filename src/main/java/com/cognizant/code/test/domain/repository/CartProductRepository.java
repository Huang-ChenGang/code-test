package com.cognizant.code.test.domain.repository;

import com.cognizant.code.test.domain.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, String> {

    void deleteAllByCartId(String cartId);
}
