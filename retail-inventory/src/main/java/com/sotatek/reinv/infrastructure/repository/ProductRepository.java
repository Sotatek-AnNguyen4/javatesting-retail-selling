package com.sotatek.reinv.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotatek.reinv.infrastructure.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
