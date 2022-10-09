package com.sotatek.reinv.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sotatek.reinv.domain.settlement.SettlementResDto;
import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
	
	// yyyy-MM-dd
	@Query(
		value = "SELECT ph.productId, SUM(ph.quantity) as quantity, p.retailId FROM product_history ph, product p WHERE ph.productId = p.id and ph.`type` = ?1 and DATE(ph.createTime)=?2 GROUP BY ph.productId", 
		nativeQuery = true)
	List<SettlementResDto> findByTypeAndCreateTime(String state, String createTime);
	
}
