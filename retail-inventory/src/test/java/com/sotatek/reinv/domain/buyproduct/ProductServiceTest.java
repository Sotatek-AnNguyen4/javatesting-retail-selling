package com.sotatek.reinv.domain.buyproduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sotatek.reinv.infrastructure.repository.ProductHistoryRepository;
import com.sotatek.reinv.infrastructure.repository.ProductRepository;
import com.sotatek.reinv.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

	@Autowired
	private BuyProductService buyProductService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private ProductHistoryRepository productHistoryRepository;
	
	@MockBean
	private OrderService orderService;
	
	@MockBean
	private PreDepositedAccountService preDepositedAccountService;
	
	@MockBean
	private RetailAccountService retailAccountService;
	
	/**
	 * List<OrdersReqDto> orders, Long customerId
	 */
	@Test
	@Order(1)
    void empty1() {
		List<OrdersReqDto> orders = null;
		Long customerId = null;
		
		ResponseData<?> result = buyProductService.buyProduct(orders, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(2)
    void empty2() {
		List<OrdersReqDto> orders = new ArrayList<>();
		Long customerId = 0L;
		
		ResponseData<?> result = buyProductService.buyProduct(orders, customerId);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
