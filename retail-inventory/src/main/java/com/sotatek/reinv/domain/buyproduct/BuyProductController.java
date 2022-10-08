package com.sotatek.reinv.domain.buyproduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("buy-product")
public class BuyProductController {
	
	@Autowired
	private BuyProductService buyProductService;

	@RequestMapping(value = "", method = RequestMethod.POST)
    public String buyProduct(@RequestBody List<OrdersReqDto> orders, @RequestHeader(value="userId") String userId) throws Exception {
		String result = buyProductService.buyProduct(orders, Long.parseLong(userId));
		if(result == null) {
			throw new Exception();
		}
        return result;
    }
}
