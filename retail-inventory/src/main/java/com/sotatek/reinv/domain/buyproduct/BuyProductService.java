package com.sotatek.reinv.domain.buyproduct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.model.Product;
import com.sotatek.reinv.infrastructure.model.ProductHistory;
import com.sotatek.reinv.infrastructure.repository.ProductHistoryRepository;
import com.sotatek.reinv.infrastructure.repository.ProductRepository;

import kong.unirest.json.JSONObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BuyProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductHistoryRepository productHistoryRepository;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PreDepositedAccountService preDepositedAccountService;
	
	@Autowired
	private RetailAccountService retailAccountService;
	
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
			
			JSONObject order = orderService.createOrder(customerId, createOrderList);
			Long orderId = order.getLong("id");
			
			preDepositedAccountService.payOrder(customerId, totalAmount);
			// callback to order
			orderService.callbackState(orderId);
			
			retailAccountService.receiveAmount(orders, orderId);
			
			orders.stream().forEach(od -> {
				ProductHistory productHistory = new ProductHistory();
				productHistory.createTime = new Date();
				productHistory.orderId = orderId;
				productHistory.quantity = od.quantity;
				productHistory.type = "buy";
				productHistory.product = od.product;
				productHistoryRepository.save(productHistory);
			});
			return order.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
