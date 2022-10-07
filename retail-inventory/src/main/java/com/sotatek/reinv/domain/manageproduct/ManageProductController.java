package com.sotatek.reinv.domain.manageproduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.reinv.domain.increateinventory.IncreateInventoryController;
import com.sotatek.reinv.domain.increateinventory.IncreateInventoryReqDto;
import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/manage-product")
public class ManageProductController {
	
	@Autowired
	private ManageProductService manageProductService;

	@RequestMapping(value = "", method = RequestMethod.POST)
    public Product manageProduct(@RequestBody Product product) throws Exception{
		Product result = manageProductService.addProduct(product);
		if(result == null) {
			throw new Exception();
		}
		return result;
    }
}
