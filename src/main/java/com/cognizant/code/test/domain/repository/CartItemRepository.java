package com.cognizant.code.test.domain.repository;

import com.cognizant.code.test.domain.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, String> {

    Optional<CartItem> findByCartIdAndProductId(String cartId, String productId);

    List<CartItem> findByCartId(String cartId);
}
