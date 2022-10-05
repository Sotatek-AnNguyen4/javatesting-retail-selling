package com.sotatek.order.infrastructure.repository;

import java.util.stream.Stream;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sotatek.order.infrastructure.model.Account;
import com.sotatek.order.infrastructure.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "false"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")
    })
    @Query("SELECT c FROM customer c")
    Stream<Customer> getAllCustomer();
	
	Customer findByToken(@Param("token") String token);
}
