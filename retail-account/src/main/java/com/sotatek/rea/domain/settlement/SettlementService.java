package com.sotatek.rea.domain.settlement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.rea.infrastructure.config.AppRunningProperties;
import com.sotatek.rea.infrastructure.model.Settlement;
import com.sotatek.rea.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.rea.infrastructure.util.GatewayConst;
import com.sotatek.rea.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import kong.unirest.Unirest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SettlementService {

	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	@Autowired
	private AppRunningProperties appRunningProperties;
	
	public ResponseData<?> settlement() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
//			calendar.add(Calendar.DATE, -1);
			Date yesterday = calendar.getTime();
			return new ResponseData<List<SettlementDto>>(HttpStatus.OK, accountHistoryRepository.findByTypeAndCreateTime("receive", dateFormat.format(yesterday)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	public List<Settlement> jobHandle() throws Exception{
		try {
			ResponseData orderResponse = Unirest.get(appRunningProperties.getBaseUrl() + "/order/settlement")
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .asObject(ResponseData.class)
				      .getBody();
			
			ResponseData inventoryResponse = Unirest.get(appRunningProperties.getBaseUrl() + "/retail-inventory/settlement")
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .asObject(ResponseData.class)
				      .getBody();
			
			ResponseData retailAccountResponse = settlement();
			
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
			List<Settlement> settlements = new ArrayList<>();
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
				settlements.add(settlement);
			}
			return settlements;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e.getMessage());
		}
	}
	
}
