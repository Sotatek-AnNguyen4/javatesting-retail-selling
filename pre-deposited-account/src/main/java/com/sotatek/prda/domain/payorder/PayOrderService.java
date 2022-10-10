package com.sotatek.prda.domain.payorder;

import java.util.Date;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.prda.infrastructure.model.Account;
import com.sotatek.prda.infrastructure.model.AccountHistory;
import com.sotatek.prda.infrastructure.model.Customer;
import com.sotatek.prda.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.prda.infrastructure.repository.AccountRepository;
import com.sotatek.prda.infrastructure.repository.CustomerRepository;
import com.sotatek.prda.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PayOrderService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	public ResponseData<?> buyProduct(Long totalAmount, Long customerId, Long orderId) {
		try {
			if(totalAmount == null) {
				throw new Exception("totalAmount doesn't exist");
			}
			if(customerId == null || customerId <= 0) {
				throw new Exception("customerId doesn't exist");
			}
			if(orderId == null || orderId <= 0) {
				throw new Exception("orderId doesn't exist");
			}
			Customer customer = customerRepository.findById(customerId).get();
			if(customer == null) {
				throw new Exception("Customer "+ customerId +" doesn't exist");
			}
			Account account = accountRepository.findByCustomerId(customerId);
			if(account == null) {
				account = new Account();
				account.balance = 0L;
				account.customer = customer;
				accountRepository.save(account);
			}
			log.info("totalAmount: {}", totalAmount);
			if(account.balance == null) {
				account.balance = 0L;
			}
			if(totalAmount > account.balance) {
				log.error("Insufficient balance");
				throw new Exception("Customer "+ customerId + " insufficient balance");
			}
			account.balance = account.balance - totalAmount;
			accountRepository.save(account);
			
			AccountHistory accountHistory = new AccountHistory();
			accountHistory.amount = totalAmount;
			accountHistory.createTime = new Date();
			accountHistory.customer = customer;
			accountHistory.type = "buy";
			accountHistory.orderId = orderId;
			accountHistoryRepository.save(accountHistory);
			return new ResponseData<AccountHistory>(HttpStatus.OK, accountHistory);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
