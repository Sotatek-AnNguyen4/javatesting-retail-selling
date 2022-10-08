package com.sotatek.reinv.domain.manageproduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.repository.ProductRepository;
import com.sotatek.reinv.infrastructure.util.GatewayConst;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ManageProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Product addProduct(Product product) {
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://localhost:8080/retail-account/retail/findByRetailId?retailId="+product.retailId)
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .asJson();
			if(response.getStatus() != 200) {
				throw new Exception();
			}
			return productRepository.save(product);
		} catch (Exception e) {
			return null;
		}
		
	}
}
