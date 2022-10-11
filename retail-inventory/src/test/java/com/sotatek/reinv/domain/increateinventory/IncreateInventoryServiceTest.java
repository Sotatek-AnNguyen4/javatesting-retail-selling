package com.sotatek.reinv.domain.increateinventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;
import com.sotatek.reinv.infrastructure.repository.ProductHistoryRepository;
import com.sotatek.reinv.infrastructure.repository.ProductRepository;
import com.sotatek.reinv.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IncreateInventoryServiceTest {

	@Autowired
	private IncreateInventoryService increateInventoryService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private ProductHistoryRepository productHistoryRepository;
	
	@Test
	@Order(1)
    void empty1() {
		Long productId = null;
		Integer quantity = null;
		
		ResponseData<?> result = increateInventoryService.increateInventory(productId, quantity);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(2)
    void empty2() {
		Long productId = 0L;
		Integer quantity = 0;
		
		ResponseData<?> result = increateInventoryService.increateInventory(productId, quantity);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(3)
    void productIsNull() {
		Long productId = 1L;
		Integer quantity = 1;
		
		when(productRepository.findById(any()))
		  .thenReturn(null);
		
		ResponseData<?> result = increateInventoryService.increateInventory(productId, quantity);
		assertThat(result.code).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(4)
    void success() {
		Long productId = 1L;
		Integer quantity = 1;
		
		when(productRepository.findById(any()))
		  .thenReturn(Optional.of(new Product()));
		
		when(productRepository.save(any()))
		  .thenReturn(new Product());
		
		when(productHistoryRepository.save(any()))
		  .thenReturn(new ProductHistory());
		
		ResponseData<?> result = increateInventoryService.increateInventory(productId, quantity);
		assertThat(result.code).isEqualTo(HttpStatus.OK);
	}
}
