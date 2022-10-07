package com.sotatek.prda.domain.synccustomer;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sotatek.prda.infrastructure.model.Account;
import com.sotatek.prda.infrastructure.model.Customer;
import com.sotatek.prda.infrastructure.model.User;
import com.sotatek.prda.infrastructure.repository.AccountRepository;
import com.sotatek.prda.infrastructure.repository.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SyncCustomerJob {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private RedisTemplate<Object, Object> redisTemplate;

	@Scheduled(fixedRate = 180000)
    @Transactional(readOnly = true)
    public void running() {
		ObjectMapper mapper = new ObjectMapper();  
		try (Stream<Customer> customerStream = customerRepository.getAllCustomer()) {
            List<Customer> listCustomer = customerStream.collect(Collectors.toList());
            listCustomer.forEach(item -> {
				try {
					redisTemplate.opsForValue().set(item.token, mapper.writeValueAsString(new User(item.id, "customer")), 5, TimeUnit.MINUTES);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			});
        } catch (Exception e) {
            log.error("[SCHEDULE] " + e);
        }
	}
}
