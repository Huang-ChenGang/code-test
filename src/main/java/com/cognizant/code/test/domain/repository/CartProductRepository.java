package com.cognizant.code.test.domain.repository;

import com.cognizant.code.test.domain.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, String> {

    Optional<CartProduct> findByCartIdAndProductId(String cartId, String productId);
}
