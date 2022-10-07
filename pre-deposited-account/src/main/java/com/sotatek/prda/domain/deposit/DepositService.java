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
	
	public AccountHistory deposit(Long value, Long customerId) {
		try {
			Customer customer = customerRepository.findById(customerId).get();
			if(customer == null) {
				throw new AccountNotFoundException();
			}
			Account account = accountRepository.findByCustomerId(customerId);
			if(account == null) {
				account = new Account();
				account.setBalance(0L);
				account.setCustomer(customer);
				accountRepository.save(account);
			}
			account.balance = account.balance + value;
			accountRepository.save(account);
			
			AccountHistory accountHistory = new AccountHistory();
			accountHistory.customer = account.customer;
			accountHistory.type = "deposit";
			accountHistory.createTime = new Date();
			accountHistory.amount = value;
			accountHistoryRepository.save(accountHistory);
			return accountHistory;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
