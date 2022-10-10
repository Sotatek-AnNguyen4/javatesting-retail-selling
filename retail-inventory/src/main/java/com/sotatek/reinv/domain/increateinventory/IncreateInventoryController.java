package com.sotatek.reinv.domain.increateinventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.reinv.infrastructure.model.ProductHistory;
import com.sotatek.reinv.infrastructure.util.ResponseData;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("increate-inventory")
public class IncreateInventoryController {
	
	@Autowired
	private IncreateInventoryService increateInventoryService;

	@RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseData<?> createOrder(@RequestBody IncreateInventoryReqDto increateInventory) throws Exception{
		return increateInventoryService.increateInventory(increateInventory.productId, increateInventory.quantity);
    }
}
