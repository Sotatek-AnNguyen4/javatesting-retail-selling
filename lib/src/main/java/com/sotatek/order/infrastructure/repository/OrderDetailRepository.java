package com.sotatek.order.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sotatek.order.domain.settlement.SettlementResDto;
import com.sotatek.order.infrastructure.model.Order;
import com.sotatek.order.infrastructure.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	// yyyy-MM-dd
	// SELECT od.productId, SUM(od.quantity * od.price)  as amount FROM order_detail od WHERE od.orderId in (SELECT o.id FROM `order` o WHERE o.state = 'sold' and DATE(o.createTime)='2022-10-09') GROUP BY od.productId;
	@Query(
		value = "SELECT od.productId AS productId, SUM(od.quantity * od.price) AS amount, SUM(od.quantity) AS quantity FROM order_detail od WHERE od.orderId in (SELECT o.id FROM `order` o WHERE o.state = ?1 and DATE(o.createTime) = ?2) GROUP BY od.productId", 
		nativeQuery = true)
	List<SettlementResDto> findByStateAndCreateTime(String state, String createTime);
}
