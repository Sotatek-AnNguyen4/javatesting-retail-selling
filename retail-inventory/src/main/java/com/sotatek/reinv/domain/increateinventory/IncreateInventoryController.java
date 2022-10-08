package com.sotatek.reinv.domain.increateinventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.reinv.infrastructure.model.ProductHistory;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("increate-inventory")
public class IncreateInventoryController {
	
	@Autowired
	private IncreateInventoryService increateInventoryService;

	@RequestMapping(value = "", method = RequestMethod.POST)
    public ProductHistory createOrder(@RequestBody IncreateInventoryReqDto increateInventory) throws Exception{
		ProductHistory result = increateInventoryService.increateInventory(increateInventory.productId, increateInventory.quantity);
		if(result == null) {
			throw new Exception();
		}
		return result;
    }
}
