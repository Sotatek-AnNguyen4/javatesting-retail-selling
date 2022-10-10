package com.sotatek.order.domain.createorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sotatek.order.infrastructure.repository.OrderDetailRepository;
import com.sotatek.order.infrastructure.repository.OrderRepository;
import com.sotatek.order.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateOrderServiceTest {

	@Autowired
//	@MockBean
	private CreateOrderService createOrderService;
	
	@MockBean
	private OrderRepository orderRepository;
	
	@MockBean
	private OrderDetailRepository orderDetailRepository;
	
	@BeforeEach                                  
    void setUp() {
//		createOrderService = new CreateOrderService();
    }
	
	@Test
	@Order(1)
    void empty() {
		List<CreateOrderReqDto> product = new ArrayList<>();
		product.addAll(Arrays.asList(
				));
		Long customerId = null;
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = createOrderService.createOrder(product, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);

    }
	
	/**
	 * CreateOrderReqDto(Long productId, Long price, Integer quantity)
	 */
	@Test
	@Order(2)
    void productIdIsNull() {
		List<CreateOrderReqDto> product = new ArrayList<>();
		product.addAll(Arrays.asList(
				new CreateOrderReqDto(null, 12000L, 1)
				));
		Long customerId = 0L;
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = createOrderService.createOrder(product, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);

    }
	
	@Test
	@Order(3)
    void priceIsNull() {
		List<CreateOrderReqDto> product = new ArrayList<>();
		product.addAll(Arrays.asList(
				new CreateOrderReqDto(1L, null, 1)
				));
		Long customerId = 0L;
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = createOrderService.createOrder(product, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);

    }
	
	@Test
	@Order(4)
    void customerIdIsNull() {
		List<CreateOrderReqDto> product = new ArrayList<>();
		product.addAll(Arrays.asList(
				new CreateOrderReqDto(1L, 12000L, null)
				));
		Long customerId = 0L;
		
		when(orderRepository.save(any()))
		  .thenReturn(new com.sotatek.order.infrastructure.model.Order());
		
		ResponseData<?> result = createOrderService.createOrder(product, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);

    }
	
}
