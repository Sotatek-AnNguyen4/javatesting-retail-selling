package com.sotatek.reinv.domain.buyproduct;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.util.GatewayConst;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@Service
public class RetailAccountService {

	public void receiveAmount(List<OrdersReqDto> orders, Long orderId) {
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
		
		
		HttpResponse<JsonNode> retailResponse = Unirest.post("http://localhost:8080/retail-account/receive-amount")
			      .header("Content-Type", "application/json")
			      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
			      .body(receiveAmountList)
			      .asJson();
		
		if(retailResponse.getStatus() != 200) {
			throw new ExceptionInInitializerError();
		}
	}
}
