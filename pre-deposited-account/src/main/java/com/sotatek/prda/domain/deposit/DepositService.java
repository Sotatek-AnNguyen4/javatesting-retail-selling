package com.sotatek.prda.domain.deposit;

import java.time.LocalDateTime;
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
public class DepositService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	public ResponseData<?> deposit(Long value, Long customerId) {
		try {
			if(value == null || value <= 0) {
				throw new Exception("value doesn't exist");
			}
			if(customerId == null || customerId <= 0) {
				throw new Exception("customerId doesn't exist");
			}
			Customer customer = customerRepository.findById(customerId).get();
			if(customer == null) {
				throw new Exception("Customer "+ customerId +" doesn't exist");
			}
			Account account = accountRepository.findByCustomerId(customerId);
			if(account == null) {
				account = new Account();
				account.setBalance(0L);
				account.setCustomer(customer);
				accountRepository.save(account);
			}
			if(account.balance == null) {
				account.balance = 0L;
			}
			account.balance = account.balance + value;
			accountRepository.save(account);
			
			AccountHistory accountHistory = new AccountHistory();
			accountHistory.customer = account.customer;
			accountHistory.type = "deposit";
			accountHistory.createTime = new Date();
			accountHistory.amount = value;
			accountHistoryRepository.save(accountHistory);
			return new ResponseData<AccountHistory>(HttpStatus.OK, accountHistory);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
