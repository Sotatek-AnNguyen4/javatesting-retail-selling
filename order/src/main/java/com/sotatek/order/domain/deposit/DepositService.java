package com.sotatek.order.domain.deposit;

import java.util.Date;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.order.infrastructure.model.Account;
import com.sotatek.order.infrastructure.model.AccountHistory;
import com.sotatek.order.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.order.infrastructure.repository.AccountRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DepositService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	public AccountHistory deposit(Long value, Long customerId) {
		try {
			Account account = accountRepository.findByCustomerId(customerId);
			if(account == null) {
				throw new AccountNotFoundException();
			}
			account.balance = account.balance + value;
			accountRepository.save(account);
			
			AccountHistory accountHistory = new AccountHistory();
			accountHistory.customer = account.customer;
			accountHistory.type = "deposit";
			accountHistory.create = new Date();
			accountHistory.amount = value;
			accountHistoryRepository.save(accountHistory);
			return accountHistory;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
