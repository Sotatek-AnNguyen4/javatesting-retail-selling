package com.sotatek.order.domain.synccustomer;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sotatek.order.infrastructure.model.Account;
import com.sotatek.order.infrastructure.model.Customer;
import com.sotatek.order.infrastructure.repository.AccountRepository;
import com.sotatek.order.infrastructure.repository.CustomerRepository;

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
		try (Stream<Customer> customerStream = customerRepository.getAllCustomer()) {
            List<Customer> listCustomer = customerStream.collect(Collectors.toList());
            listCustomer.forEach(item -> redisTemplate.opsForValue().set(item.token, item.getId(), 5, TimeUnit.MINUTES));
        } catch (Exception e) {
            log.error("[SCHEDULE] " + e);
        }
	}
}
