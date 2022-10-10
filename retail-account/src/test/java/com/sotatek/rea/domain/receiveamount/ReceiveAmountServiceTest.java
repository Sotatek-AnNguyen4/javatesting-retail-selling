package com.sotatek.rea.domain.receiveamount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sotatek.rea.infrastructure.model.Account;
import com.sotatek.rea.infrastructure.model.AccountHistory;
import com.sotatek.rea.infrastructure.model.Retail;
import com.sotatek.rea.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.rea.infrastructure.repository.AccountRepository;
import com.sotatek.rea.infrastructure.repository.RetailRepository;
import com.sotatek.rea.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReceiveAmountServiceTest {

	@Autowired
	private ReceiveAmountService receiveAmountService;
	
	@MockBean
	private AccountRepository accountRepository;
	
	@MockBean
	private AccountHistoryRepository accountHistoryRepository;
	
	@MockBean
	private RetailRepository retailRepository;
	
	@Test
	@Order(1)
    void empty1() {
		List<ReceiveAmountReqDto> request = null;
		
		ResponseData<?> result = receiveAmountService.receiveAmount(request);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(2)
    void empty2() {
		List<ReceiveAmountReqDto> request = new ArrayList<>();
		request.addAll(Arrays.asList(
				));
		
		ResponseData<?> result = receiveAmountService.receiveAmount(request);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(3)
    void empty3() {
		List<ReceiveAmountReqDto> request = new ArrayList<>();
		request.addAll(Arrays.asList(
				new ReceiveAmountReqDto()
				));
		
		ResponseData<?> result = receiveAmountService.receiveAmount(request);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	/**
	 * 	public Long retailId;
	 *  public Long amount;
	 *  public Long orderId;
	 */
	@Test
	@Order(4)
    void empty4() {
		List<ReceiveAmountReqDto> request = new ArrayList<>();
		request.addAll(Arrays.asList(
				new ReceiveAmountReqDto(0L, 0L, 0L)
				));
		
		ResponseData<?> result = receiveAmountService.receiveAmount(request);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(5)
    void retailIsNull() {
		List<ReceiveAmountReqDto> request = new ArrayList<>();
		request.addAll(Arrays.asList(
				new ReceiveAmountReqDto(1L, 100000L, 1L)
				));
		
		when(retailRepository.findById(any()))
		  .thenReturn(null);
		
		ResponseData<?> result = receiveAmountService.receiveAmount(request);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(6)
    void accountIsNull() {
		List<ReceiveAmountReqDto> request = new ArrayList<>();
		request.addAll(Arrays.asList(
				new ReceiveAmountReqDto(1L, 100000L, 1L)
				));
		
		when(retailRepository.findById(any()))
		  .thenReturn(Optional.of(new Retail()));
		
		when(accountRepository.findByRetailId(any()))
		  .thenReturn(null);
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		when(accountHistoryRepository.save(any()))
		  .thenReturn(new AccountHistory());
		
		ResponseData<?> result = receiveAmountService.receiveAmount(request);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@Order(7)
    void success() {
		List<ReceiveAmountReqDto> request = new ArrayList<>();
		request.addAll(Arrays.asList(
				new ReceiveAmountReqDto(1L, 100000L, 1L)
				));
		
		when(retailRepository.findById(any()))
		  .thenReturn(Optional.of(new Retail()));
		
		when(accountRepository.findByRetailId(any()))
		  .thenReturn(new Account());
		
		when(accountRepository.save(any()))
		  .thenReturn(new Account());
		
		when(accountHistoryRepository.save(any()))
		  .thenReturn(new AccountHistory());
		
		ResponseData<?> result = receiveAmountService.receiveAmount(request);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
}
