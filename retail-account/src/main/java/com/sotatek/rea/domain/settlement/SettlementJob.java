package com.sotatek.rea.domain.settlement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sotatek.rea.infrastructure.config.AppRunningProperties;
import com.sotatek.rea.infrastructure.model.AccountHistory;
import com.sotatek.rea.infrastructure.model.Settlement;
import com.sotatek.rea.infrastructure.repository.RetailRepository;
import com.sotatek.rea.infrastructure.repository.SettlementRepository;
import com.sotatek.rea.infrastructure.util.GatewayConst;
import com.sotatek.rea.infrastructure.util.ResponseData;

import kong.unirest.Unirest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SettlementJob {
	
	@Autowired
	private SettlementService settlementService;
	
	@Autowired
	private SettlementRepository settlementRepository;
	
	@Scheduled(cron = "0 0 1 * * ?")
//	@Scheduled(fixedRate = 5000)
	public void running() {
		try {
			List<Settlement> settlements = settlementService.jobHandle();
			for(Settlement settlement: settlements) {
				settlementRepository.save(settlement);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
