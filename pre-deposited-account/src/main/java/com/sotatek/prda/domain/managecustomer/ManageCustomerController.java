package com.sotatek.prda.domain.managecustomer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.prda.domain.managecustomer.ManageCustomerService;
import com.sotatek.prda.infrastructure.model.Customer;
import com.sotatek.prda.infrastructure.util.ResponseData;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("customer")
public class ManageCustomerController {

	@Autowired
	private ManageCustomerService manageCustomerService;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData<?> customerAdd(@RequestBody List<Customer> customers) throws Exception {
        return manageCustomerService.add(customers);
    }
	
	@RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseData<?> customerUpdate(@RequestBody Customer customer) throws Exception {
		return manageCustomerService.update(customer);
    }
}
