package com.sotatek.reinv.domain.buyproduct;

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
import kong.unirest.json.JSONObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderService {
	
	@Autowired
	private AppRunningProperties appRunningProperties;

	public Map<String, Object> createOrder(Long customerId, List<CreateOrderResDto> createOrderList) throws Exception {
		try {
			ResponseData orderResponse = Unirest.post(appRunningProperties.getBaseUrl() + "/order/create-order")
				      .header("Content-Type", "application/json")
				      .header("userId", customerId.toString())
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .body(createOrderList)
				      .asObject(ResponseData.class)
				      .getBody();
			if(orderResponse.code != 200) {
				throw new Exception(orderResponse.data.toString());
			}
			Map<String, Object> object = (Map<String, Object>) orderResponse.data;
			return object;
		} catch (Exception e) {
			throw new Exception("Error calling order/create-order: " + e.getMessage());
		}
	}
	
	public Map<String, Object> callbackState(Long orderId) throws Exception {
		try {
			ResponseData response = Unirest.post(appRunningProperties.getBaseUrl() + "/order/callback-state")
				      .header("Content-Type", "application/json")
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .body(new CallbackStateResDto(orderId, "sold"))
				      .asObject(ResponseData.class)
				      .getBody();
			if(response.code != 200) {
				throw new Exception(response.data.toString());
			}
			Map<String, Object> object = (Map<String, Object>) response.data;
			return object;
		} catch (Exception e) {
			throw new Exception("Error calling order/callback-state: " + e.getMessage());
		}
	}
}
