package com.sotatek.rea.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sotatek.rea.domain.settlement.SettlementDto;
import com.sotatek.rea.infrastructure.model.AccountHistory;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
	// yyyy-MM-dd
	@Query(
		value = "SELECT ah.retailId, SUM(ah.amount) as amount FROM account_history ah WHERE ah.`type` = ?1 and DATE(ah.createTime) = ?2 GROUP BY ah.retailId", 
		nativeQuery = true)
	List<SettlementDto> findByTypeAndCreateTime(String type, String createTime);
}
