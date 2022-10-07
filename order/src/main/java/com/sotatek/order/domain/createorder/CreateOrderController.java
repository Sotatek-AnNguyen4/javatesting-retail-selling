package com.sotatek.order.domain.createorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.order.infrastructure.model.Order;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/create-order")
public class CreateOrderController {
	
	@Autowired
	private CreateOrderService createOrderService;

	@RequestMapping(value = "", method = RequestMethod.POST)
    public Order createOrder(@RequestHeader(value="userId") String userId, @RequestBody List<CreateOrderReqDto> products) throws Exception {
		Order result = createOrderService.createOrder(products, Long.parseLong(userId));
		if(result == null) {
			throw new Exception();
		}
        return result;
    }
}
