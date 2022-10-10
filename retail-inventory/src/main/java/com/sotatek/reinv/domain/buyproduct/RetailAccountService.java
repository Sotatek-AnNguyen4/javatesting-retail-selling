package com.sotatek.reinv.domain.buyproduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.config.AppRunningProperties;
import com.sotatek.reinv.infrastructure.util.GatewayConst;
import com.sotatek.reinv.infrastructure.util.ResponseData;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RetailAccountService {

	@Autowired
	private AppRunningProperties appRunningProperties;
	
	public Object receiveAmount(List<OrdersReqDto> orders, Long orderId) throws Exception {
		try {
			List<ReceiveAmountResDto> receiveAmountList = new ArrayList<>();
			for(OrdersReqDto a: orders) {
				Boolean c = false;
				for(ReceiveAmountResDto b: receiveAmountList) {
					if(a.product.retailId == b.retailId) {
						c = true;
						break;
					}
				}
				if(!c) {
					ReceiveAmountResDto d = new ReceiveAmountResDto();
					d.retailId = a.product.retailId;
					d.orderId = orderId;
					receiveAmountList.add(d);
				}
			}
			
			for(ReceiveAmountResDto a: receiveAmountList) {
				Long amount = 0L;
				for(OrdersReqDto b: orders) {
					if(a.retailId == b.product.retailId) {
						amount += b.product.price * b.quantity;
					}
				}
				a.amount = amount;
			}
			
			
			ResponseData response = Unirest.post(appRunningProperties.getBaseUrl() + "/retail-account/receive-amount")
				      .header("Content-Type", "application/json")
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .body(receiveAmountList)
				      .asObject(ResponseData.class)
				      .getBody();
			
			if(response.code != 200) {
				throw new Exception(response.data.toString());
			}
			String object = response.data.toString();
			return object;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("Error calling retail-account/receive-amount: " + e.getMessage());
		}
	}
}
