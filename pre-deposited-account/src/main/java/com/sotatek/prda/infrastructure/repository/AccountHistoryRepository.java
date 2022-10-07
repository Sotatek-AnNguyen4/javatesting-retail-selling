package com.sotatek.prda.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotatek.prda.infrastructure.model.AccountHistory;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

}
