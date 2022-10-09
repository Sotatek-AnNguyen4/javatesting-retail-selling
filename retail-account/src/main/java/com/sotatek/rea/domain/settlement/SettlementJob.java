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
	
	@Autowired
	private AppRunningProperties appRunningProperties;
	
	@Scheduled(cron = "0 0 1 * * ?")
//	@Scheduled(fixedRate = 5000)
	public void running() {
		try {
			ResponseData orderResponse = Unirest.get(appRunningProperties.getBaseUrl() + "/order/settlement")
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .asObject(ResponseData.class)
				      .getBody();
			
			ResponseData inventoryResponse = Unirest.get(appRunningProperties.getBaseUrl() + "/retail-inventory/settlement")
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .asObject(ResponseData.class)
				      .getBody();
			
			ResponseData retailAccountResponse = settlementService.settlement();
			
			if(orderResponse.code != 200) {
				throw new Exception(orderResponse.data.toString());
			}
			if(inventoryResponse.code != 200) {
				throw new Exception(inventoryResponse.data.toString());
			}
			if(retailAccountResponse.code != 200) {
				throw new Exception(retailAccountResponse.data.toString());
			}
			
			List<Map<String, Object>> orderRes = (List<Map<String, Object>>) orderResponse.data;  // amount
			
			List<Map<String, Object>> productRes = (List<Map<String, Object>>) inventoryResponse.data; // quantity
			
			List<SettlementDto> retailRes = (List<SettlementDto>) retailAccountResponse.data;
			
			for(Map<String, Object> product: productRes) {
				for(Map<String, Object> order: orderRes) {
					if(product.get("productId").equals(order.get("productId"))) {
						product.put("amount", order.get("amount"));
						break;
					}
				}
			}
			for(SettlementDto retail: retailRes) {
				Settlement settlement = new Settlement();
				settlement.retailId = retail.getRetailId();
				for(Map<String, Object> product: productRes) {
					if(Double.parseDouble(product.get("retailId").toString()) == retail.getRetailId()) {
						if(Double.parseDouble(product.get("amount").toString()) == retail.getAmount()) {
							settlement.state = "match";
						} else {
							settlement.state = "unmatch";
						}
						break;
					}
				}
				settlement.createTime = new Date();
				settlementRepository.save(settlement);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
