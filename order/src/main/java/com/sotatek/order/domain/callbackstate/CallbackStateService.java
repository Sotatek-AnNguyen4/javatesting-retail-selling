package com.sotatek.order.domain.callbackstate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.order.infrastructure.model.Order;
import com.sotatek.order.infrastructure.repository.OrderRepository;
import com.sotatek.order.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;

@Service
public class CallbackStateService {
	
	@Autowired
	private OrderRepository orderRepository;

	public ResponseData<?> callbackState(Long orderId, String state) {
		try {
			if(orderId == null || orderId <= 0) {
				throw new Exception("orderId is in wrong format");
			}
			if(state.isBlank()) {
				throw new Exception("state is in wrong format");
			}
			Order order = orderRepository.findById(orderId).get();
			order.state = state;
			order.createTime = new Date();
			orderRepository.save(order);
			return new ResponseData<Order>(HttpStatus.OK, order);
		} catch (Exception e) {
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
