package com.sotatek.reinv.domain.buyproduct;

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
public class PreDepositedAccountService {
	
	@Autowired
	private AppRunningProperties appRunningProperties;

	public Map<String, Object> payOrder(Long customerId, Long totalAmount) throws Exception{
		try {
			ResponseData response = Unirest.post(appRunningProperties.getBaseUrl() + "/pre-deposited-account/pay-order")
				      .header("Content-Type", "application/json")
				      .header("userId", customerId.toString())
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .body(new PayOrderResDto(totalAmount, customerId))
				      .asObject(ResponseData.class)
				      .getBody();
			if(response.code != 200) {
				throw new Exception(response.data.toString());
			}
			Map<String, Object> object = (Map<String, Object>) response.data;
			return object;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("Error calling pre-deposited-account/pay-order: " + e.getMessage());
		}
	}
}
