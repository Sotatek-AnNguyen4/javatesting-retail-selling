package com.sotatek.order.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotatek.order.infrastructure.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
}
