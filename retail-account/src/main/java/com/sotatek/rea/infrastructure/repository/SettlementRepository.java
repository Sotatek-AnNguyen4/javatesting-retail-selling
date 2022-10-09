package com.sotatek.rea.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotatek.rea.infrastructure.model.AccountHistory;
import com.sotatek.rea.infrastructure.model.Settlement;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

}
