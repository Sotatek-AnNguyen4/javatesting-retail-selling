package com.sotatek.reinv.domain.buyproduct;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.util.GatewayConst;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

@Service
public class OrderService {

	public JSONObject createOrder(Long customerId, List<CreateOrderResDto> createOrderList) {
		HttpResponse<JsonNode> orderResponse = Unirest.post("http://localhost:8080/order/create-order")
			      .header("Content-Type", "application/json")
			      .header("userId", customerId.toString())
			      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
			      .body(createOrderList)
			      .asJson();
		
		if(orderResponse.getStatus() != 200) {
			throw new ExceptionInInitializerError();
		}
		JsonNode rootNode = orderResponse.getBody();
		JSONObject rootObj = rootNode.getObject();
		Long orderId = rootObj.getLong("id");
		return rootObj;
	}
	
	public void callbackState(Long orderId) {
		Unirest.post("http://localhost:8080/order/callback-state")
	      .header("Content-Type", "application/json")
	      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
	      .body(new CallbackStateResDto(orderId, "sold"));
	}
}
