package com.sotatek.reinv.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
	
}
