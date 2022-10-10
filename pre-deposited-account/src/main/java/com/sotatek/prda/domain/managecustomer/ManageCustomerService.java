package com.sotatek.prda.domain.managecustomer;

import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sotatek.prda.domain.synccustomer.SyncCustomerJob;
import com.sotatek.prda.infrastructure.model.AccountHistory;
import com.sotatek.prda.infrastructure.model.Customer;
import com.sotatek.prda.infrastructure.repository.CustomerRepository;
import com.sotatek.prda.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ManageCustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	public ResponseData<?> add(Customer customer) {
		try {
			customer.token = org.apache.commons.codec.digest.DigestUtils.sha256Hex(customer.toString() + UUID.randomUUID().toString());
			customerRepository.save(customer);
			return new ResponseData<Customer>(HttpStatus.OK, customer);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	public ResponseData<?> update(Customer customer) {
		try {
			Customer customerOnDB = customerRepository.findById(customer.getId()).get();
			customerOnDB.email = customer.email;
			customerOnDB.name = customer.name;
			customerOnDB.phone = customer.phone;
			customerRepository.save(customerOnDB);
			return new ResponseData<Customer>(HttpStatus.OK, customerOnDB);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
}
