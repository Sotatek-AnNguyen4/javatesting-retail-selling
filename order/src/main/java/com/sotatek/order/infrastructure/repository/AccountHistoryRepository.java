package com.sotatek.order.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotatek.order.infrastructure.model.AccountHistory;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

}
