package com.sotatek.order.domain.callbackstate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sotatek.order.domain.createorder.CreateOrderReqDto;
import com.sotatek.order.infrastructure.repository.OrderRepository;
import com.sotatek.order.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CallbackStateServiceTest {

	@Autowired
	private CallbackStateService callbackStateService;
	
	@MockBean
	private OrderRepository orderRepository;
	
	@BeforeEach                                  
    void setUp() {
//		createOrderService = new CreateOrderService();
    }
	// Long orderId, String state
	@Test
	@Order(1)
    void emptyCase1() {
		Long orderId = null;
		String state = null;
		
		when(orderRepository.findById(any()))
		  .thenReturn(Optional.of(new com.sotatek.order.infrastructure.model.Order()));
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = callbackStateService.callbackState(orderId, state);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
    }
	
	@Test
	@Order(2)
    void emptyCase2() {
		Long orderId = null;
		String state = "";
		
		when(orderRepository.findById(any()))
		  .thenReturn(Optional.of(new com.sotatek.order.infrastructure.model.Order()));
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = callbackStateService.callbackState(orderId, state);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
    }
	
	@Test
	@Order(3)
    void emptyCase3() {
		Long orderId = 0L;
		String state = "";

		when(orderRepository.findById(any()))
		  .thenReturn(Optional.of(new com.sotatek.order.infrastructure.model.Order()));
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = callbackStateService.callbackState(orderId, state);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
    }
	
	@Test
	@Order(4)
    void orderIdIsNull() {
		Long orderId = null;
		String state = "sold";
		
		when(orderRepository.findById(any()))
		  .thenReturn(Optional.of(new com.sotatek.order.infrastructure.model.Order()));
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = callbackStateService.callbackState(orderId, state);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
    }
	
	@Test
	@Order(5)
    void stateIsNull1() {
		Long orderId = 1L;
		String state = null;
		
		when(orderRepository.findById(any()))
		  .thenReturn(Optional.of(new com.sotatek.order.infrastructure.model.Order()));
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = callbackStateService.callbackState(orderId, state);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
    }
	
	@Test
	@Order(6)
    void stateIsNull2() {
		Long orderId = 1L;
		String state = "";
		
		when(orderRepository.findById(any()))
		  .thenReturn(Optional.of(new com.sotatek.order.infrastructure.model.Order()));
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = callbackStateService.callbackState(orderId, state);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
    }
	
	@Test
	@Order(7)
    void success() {
		Long orderId = 1L;
		String state = "sold";
		
		when(orderRepository.findById(any()))
		  .thenReturn(Optional.of(new com.sotatek.order.infrastructure.model.Order()));
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = callbackStateService.callbackState(orderId, state);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
    }
}
