package com.sotatek.rea.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sotatek.rea.infrastructure.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByRetailId(@Param("retailId") Long retailId);
	
}
