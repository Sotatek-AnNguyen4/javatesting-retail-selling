package com.sotatek.reinv.domain.buyproduct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.order.domain.createorder.PayOrderResDto;
import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;
import com.sotatek.reinv.infrastructure.repository.ProductHistoryRepository;
import com.sotatek.reinv.infrastructure.repository.ProductRepository;
import com.sotatek.reinv.infrastructure.util.GatewayConst;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BuyProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductHistoryRepository productHistoryRepository;
	
	public String buyProduct(List<OrdersReqDto> orders, Long customerId) {
		try {
			List<CreateOrderResDto> createOrderList = new ArrayList<>();
			Long totalAmount = 0L;
			for (OrdersReqDto order : orders) {
				Product product = productRepository.findById(order.productId).get();
				if(product == null && product.quantity < order.quantity) {
					throw new ExceptionInInitializerError();
				}
				CreateOrderResDto createOrder = new CreateOrderResDto();
				createOrder.productId = product.id;
				createOrder.price = product.price;
				createOrder.quantity = order.quantity;
				createOrderList.add(createOrder);

				order.product = product;
				totalAmount += product.price * order.quantity;
			}
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
			
			HttpResponse<JsonNode> customerResponse = Unirest.post("http://localhost:8080/pre-deposited-account/pay-order")
				      .header("Content-Type", "application/json")
				      .header("userId", customerId.toString())
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .body(new PayOrderResDto(totalAmount, customerId))
				      .asJson();
			if(customerResponse.getStatus() != 200) {
				throw new ExceptionInInitializerError();
			}
			// callback to order
			Unirest.post("http://localhost:8080/order/callback-state")
		      .header("Content-Type", "application/json")
		      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
		      .body(new CallbackStateResDto(orderId, "sold"));
			
			HttpResponse<JsonNode> customerResponse = Unirest.post("http://localhost:8080/retail-account/receive-amount")
				      .header("Content-Type", "application/json")
				      .header("userId", customerId.toString())
				      .header("Authorization", "Bearer " + GatewayConst.TOKEN_ADMIN)
				      .body(new PayOrderResDto(totalAmount, customerId))
				      .asJson();
			
			orders.stream().forEach(order -> {
				ProductHistory productHistory = new ProductHistory();
				productHistory.createTime = new Date();
				productHistory.orderId = orderId;
				productHistory.quantity = order.quantity;
				productHistory.type = "buy";
				productHistory.product = order.product;
				productHistoryRepository.save(productHistory);
			});
			return rootObj.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
