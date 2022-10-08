package com.sotatek.reinv.domain.buyproduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.util.GatewayConst;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@Service
public class PreDepositedAccountService {

	public void payOrder(Long customerId, Long totalAmount) {
		HttpResponse<JsonNode> customerResponse = Unirest.post("http://localhost:8080/pre-deposited-account/pay-order")
			      .header("Content-Type", "application/json")
			      .header("userId", customerId.toString())
			      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
			      .body(new PayOrderResDto(totalAmount, customerId))
			      .asJson();
		if(customerResponse.getStatus() != 200) {
			throw new ExceptionInInitializerError();
		}
	}
}
