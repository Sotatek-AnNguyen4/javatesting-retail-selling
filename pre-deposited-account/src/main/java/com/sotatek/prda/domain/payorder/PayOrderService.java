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
	
	public AccountHistory buyProduct(Long totalAmount, Long customerId, Long orderId) {
		try {
			Customer customer = customerRepository.findById(customerId).get();
			if(customer == null) {
				throw new AccountNotFoundException();
			}
			Account account = accountRepository.findByCustomerId(customerId);
			if(account == null) {
				throw new AccountNotFoundException();
			}
			if(totalAmount > account.balance) {
				log.error("Insufficient balance");
				throw new AccountNotFoundException();
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
			return accountHistory;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
