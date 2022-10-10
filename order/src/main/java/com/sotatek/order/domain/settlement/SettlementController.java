package com.sotatek.order.domain.settlement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.order.domain.createorder.CreateOrderController;
import com.sotatek.order.domain.createorder.CreateOrderReqDto;
import com.sotatek.order.infrastructure.util.ResponseData;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("settlement")
public class SettlementController {

	@Autowired
	private SettlementService settlementService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData<?> createOrder() throws Exception {
        return settlementService.settlement();
    }
}
