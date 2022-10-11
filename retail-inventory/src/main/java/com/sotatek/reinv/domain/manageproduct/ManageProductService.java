package com.sotatek.reinv.domain.manageproduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.config.AppRunningProperties;
import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;
import com.sotatek.reinv.infrastructure.repository.ProductRepository;
import com.sotatek.reinv.infrastructure.util.GatewayConst;
import com.sotatek.reinv.infrastructure.util.ResponseData;

import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ManageProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AppRunningProperties appRunningProperties;
	
	public ResponseData<?> addProduct(Product product) {
		try {
			ResponseData response = Unirest.get(appRunningProperties.getBaseUrl() + "/retail-account/retail/findByRetailId?retailId="+product.retailId)
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .asObject(ResponseData.class)
				      .getBody();
			if(response.code != 200) {
				throw new Exception(response.data.toString());
			}
			return new ResponseData<Product>(HttpStatus.OK, productRepository.save(product));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
	
	public ResponseData<?> addProduct(List<Product> products) {
		try {
			for(Product product: products) {
				this.addProduct(product);
			}
			return new ResponseData<String>(HttpStatus.OK, "Done");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
}
