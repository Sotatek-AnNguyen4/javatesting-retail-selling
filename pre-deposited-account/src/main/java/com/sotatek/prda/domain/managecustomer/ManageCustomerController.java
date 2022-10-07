package com.sotatek.prda.domain.managecustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.prda.domain.managecustomer.ManageCustomerService;
import com.sotatek.prda.infrastructure.model.Customer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/customer")
public class ManageCustomerController {

	@Autowired
	private ManageCustomerService manageCustomerService;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public Customer customerAdd(@RequestBody Customer customer) throws Exception {
		Customer result = manageCustomerService.add(customer);
		if(result == null) {
			throw new Exception();
		}
        return result;
    }
	
	@RequestMapping(value = "update", method = RequestMethod.PUT)
    public Customer customerUpdate(@RequestBody Customer customer) throws Exception {
		Customer result = manageCustomerService.update(customer);
		if(result == null) {
			throw new Exception();
		}
        return result;
    }
}
