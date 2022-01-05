package com.cognizant.code.test.domain.repository;

import com.cognizant.code.test.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
