package com.sotatek.rea.domain.settlement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.rea.infrastructure.model.Settlement;
import com.sotatek.rea.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("settlement")
public class SettlementController {

	@Autowired
	private SettlementService settlementService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData<?> settlement() throws Exception {
        return settlementService.settlement();
    }
	
	@RequestMapping(value = "manual-trigger", method = RequestMethod.GET)
    public ResponseData<?> jobTrigger() throws Exception {
		try {
			return new ResponseData<List<Settlement>>(HttpStatus.OK, settlementService.jobHandle());
		} catch (Exception e) {
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
        
    }
}
