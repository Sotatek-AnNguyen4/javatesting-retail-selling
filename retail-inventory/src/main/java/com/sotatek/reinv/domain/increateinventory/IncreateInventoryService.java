package com.sotatek.reinv.domain.increateinventory;

import java.util.Date;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;
import com.sotatek.reinv.infrastructure.repository.ProductHistoryRepository;
import com.sotatek.reinv.infrastructure.repository.ProductRepository;
import com.sotatek.reinv.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class IncreateInventoryService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductHistoryRepository productHistoryRepository;
	
	public ResponseData<?> increateInventory(Long productId, Integer quantity) {
		try {
			if(productId == null || productId <= 0) {
				throw new Exception("productId doesn't exist");
			}
			if(quantity == null || quantity <= 0) {
				throw new Exception("quantity doesn't exist");
			}
			Product product = productRepository.findById(productId).get();
			if(product == null) {
				throw new Exception("Product "+ productId +" doesn't exist");
			}
			if(product.quantity == null) {
				product.quantity = 0;
			}
			product.quantity = product.quantity + quantity;
			productRepository.save(product);
			
			ProductHistory productHistory = new ProductHistory();
			productHistory.createTime = new Date();
			productHistory.product = product;
			productHistory.type = "increate";
			productHistory.quantity = quantity;
			productHistoryRepository.save(productHistory);
			return new ResponseData<ProductHistory>(HttpStatus.OK, productHistory);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
