package com.sotatek.prda.domain.deposit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sotatek.prda.infrastructure.model.Account;
import com.sotatek.prda.infrastructure.model.Customer;
import com.sotatek.prda.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.prda.infrastructure.repository.AccountRepository;
import com.sotatek.prda.infrastructure.repository.CustomerRepository;
import com.sotatek.prda.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepositServiceTest {

	@Autowired
	private DepositService depositService;
	
	@MockBean
	private AccountRepository accountRepository;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@MockBean
	private AccountHistoryRepository accountHistoryRepository;
	
	@Test
	@Order(1)
    void empty1() {
		Long value = null;
		Long customerId = null;
		
		ResponseData<?> result = depositService.deposit(value, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(2)
    void empty2() {
		Long value = 0L;
		Long customerId = 0L;
		
		ResponseData<?> result = depositService.deposit(value, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(3)
    void customerIsNull() {
		Long value = 100000L;
		Long customerId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(null);
		
		ResponseData<?> result = depositService.deposit(value, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(4)
    void accountIsNull() {
		Long value = 100000L;
		Long customerId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(Optional.of(new Customer()));
		
		when(accountRepository.findByCustomerId(any()))
		  .thenReturn(null);
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		ResponseData<?> result = depositService.deposit(value, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@Order(5)
    void success1() {
		Long value = 100000L;
		Long customerId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(Optional.of(new Customer()));
		
		when(accountRepository.findByCustomerId(any()))
		  .thenReturn(new Account(1L, null, 100000L));
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		ResponseData<?> result = depositService.deposit(value, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@Order(6)
    void success2() {
		Long value = 100000L;
		Long customerId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(Optional.of(new Customer()));
		
		when(accountRepository.findByCustomerId(any()))
		  .thenReturn(new Account());
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		ResponseData<?> result = depositService.deposit(value, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
}
