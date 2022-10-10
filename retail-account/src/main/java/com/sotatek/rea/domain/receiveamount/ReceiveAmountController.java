package com.sotatek.rea.domain.receiveamount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.rea.infrastructure.model.AccountHistory;
import com.sotatek.rea.infrastructure.model.Retail;
import com.sotatek.rea.infrastructure.util.ResponseData;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("receive-amount")
public class ReceiveAmountController {

	@Autowired
	private ReceiveAmountService receiveAmountService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseData<?> receiveAmount(@RequestBody List<ReceiveAmountReqDto> request) throws Exception {
        return receiveAmountService.receiveAmount(request);
    }
}
