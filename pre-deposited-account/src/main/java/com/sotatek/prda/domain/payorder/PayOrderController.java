package com.sotatek.prda.domain.payorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.prda.domain.deposit.DepositReqDto;
import com.sotatek.prda.domain.deposit.DepositService;
import com.sotatek.prda.domain.managecustomer.ManageCustomerService;
import com.sotatek.prda.infrastructure.model.AccountHistory;
import com.sotatek.prda.infrastructure.model.Customer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/pay-order")
public class PayOrderController {

	@Autowired
	private PayOrderService buyProductService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
    public AccountHistory buyProduct(@RequestHeader(value="userId") String userId, @RequestBody PayOrderReqDto request) throws Exception {
		AccountHistory result = buyProductService.buyProduct(request.totalAmount, Long.parseLong(userId), request.orderId);
		if(result == null) {
			throw new Exception();
		}
        return result;
    }

}
