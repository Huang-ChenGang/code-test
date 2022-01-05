package com.cognizant.code.test.domain.repository;

import com.cognizant.code.test.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByCustomerId(String customerId);
}
