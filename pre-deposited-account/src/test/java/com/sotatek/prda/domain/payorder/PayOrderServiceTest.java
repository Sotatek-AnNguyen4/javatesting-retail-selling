package com.sotatek.prda.domain.payorder;

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
import com.sotatek.prda.infrastructure.model.AccountHistory;
import com.sotatek.prda.infrastructure.model.Customer;
import com.sotatek.prda.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.prda.infrastructure.repository.AccountRepository;
import com.sotatek.prda.infrastructure.repository.CustomerRepository;
import com.sotatek.prda.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PayOrderServiceTest {

	@Autowired
	private PayOrderService payOrderService;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@MockBean
	private AccountRepository accountRepository;
	
	@MockBean
	private AccountHistoryRepository accountHistoryRepository;
	
	@Test
	@Order(1)
    void empty1() {
		Long totalAmount = null;
		Long customerId = null;
		Long orderId = null;
		
		ResponseData<?> result = payOrderService.buyProduct(totalAmount, customerId, orderId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(2)
    void empty2() {
		Long totalAmount = 0L;
		Long customerId = 0L;
		Long orderId = 0L;
		
		ResponseData<?> result = payOrderService.buyProduct(totalAmount, customerId, orderId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(3)
    void empty3() {
		Long totalAmount = 0L;
		Long customerId = 1L;
		Long orderId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(Optional.of(new Customer()));
		
		when(accountRepository.findByCustomerId(any()))
		  .thenReturn(new Account());
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		when(accountHistoryRepository.save(any()))
		  .thenReturn(new AccountHistory());
		
		ResponseData<?> result = payOrderService.buyProduct(totalAmount, customerId, orderId);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@Order(4)
    void customerIsNull() {
		Long totalAmount = 100000L;
		Long customerId = 1L;
		Long orderId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(null);
		
		ResponseData<?> result = payOrderService.buyProduct(totalAmount, customerId, orderId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(5)
    void accountIsNull() {
		Long totalAmount = 100000L;
		Long customerId = 1L;
		Long orderId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(Optional.of(new Customer()));
		
		when(accountRepository.findByCustomerId(any()))
		  .thenReturn(null);
		
		ResponseData<?> result = payOrderService.buyProduct(totalAmount, customerId, orderId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(6)
    void insufficient() {
		Long totalAmount = 100000L;
		Long customerId = 1L;
		Long orderId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(Optional.of(new Customer()));
		
		when(accountRepository.findByCustomerId(any()))
		  .thenReturn(new Account(1L, null, 50000L));
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		when(accountHistoryRepository.save(any()))
		  .thenReturn(new AccountHistory());
		
		ResponseData<?> result = payOrderService.buyProduct(totalAmount, customerId, orderId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(7)
    void success() {
		Long totalAmount = 100000L;
		Long customerId = 1L;
		Long orderId = 1L;
		
		when(customerRepository.findById(any()))
		  .thenReturn(Optional.of(new Customer()));
		
		when(accountRepository.findByCustomerId(any()))
		  .thenReturn(new Account(1L, null, 200000L));
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		when(accountHistoryRepository.save(any()))
		  .thenReturn(new AccountHistory());
		
		ResponseData<?> result = payOrderService.buyProduct(totalAmount, customerId, orderId);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
}
