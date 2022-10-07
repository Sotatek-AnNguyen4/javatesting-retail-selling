package com.sotatek.prda.domain.managecustomer;

import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sotatek.prda.domain.synccustomer.SyncCustomerJob;
import com.sotatek.prda.infrastructure.model.AccountHistory;
import com.sotatek.prda.infrastructure.model.Customer;
import com.sotatek.prda.infrastructure.repository.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ManageCustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	public Customer add(Customer customer) {
		try {
			customer.token = org.apache.commons.codec.digest.DigestUtils.sha256Hex(customer.toString() + UUID.randomUUID().toString());
			customerRepository.save(customer);
			return customer;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	public Customer update(Customer customer) {
		try {
			Customer customerOnDB = customerRepository.findById(customer.getId()).get();
			customerOnDB.email = customer.email;
			customerOnDB.name = customer.name;
			customerOnDB.phone = customer.phone;
			customerRepository.save(customerOnDB);
			return customer;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
}
