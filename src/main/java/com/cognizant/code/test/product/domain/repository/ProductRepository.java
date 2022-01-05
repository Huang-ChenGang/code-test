package com.cognizant.code.test.product.domain.repository;

import com.cognizant.code.test.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
