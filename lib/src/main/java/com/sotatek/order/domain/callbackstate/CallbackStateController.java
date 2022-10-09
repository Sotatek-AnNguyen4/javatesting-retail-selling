package com.sotatek.order.domain.callbackstate;

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
@RequestMapping("callback-state")
public class CallbackStateController {

	@Autowired
	private CallbackStateService callbackStateService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseData<?> createOrder(@RequestBody CallbackStateReqDto request) throws Exception {
        return callbackStateService.callbackState(request.orderId, request.state);
    }
}
