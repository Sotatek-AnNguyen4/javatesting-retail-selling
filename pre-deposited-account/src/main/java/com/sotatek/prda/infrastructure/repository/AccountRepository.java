package com.sotatek.prda.infrastructure.repository;

import java.util.stream.Stream;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sotatek.prda.infrastructure.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByCustomerId(@Param("customerId") Long customerId);
	
}
