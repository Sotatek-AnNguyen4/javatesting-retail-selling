package com.sotatek.rea.domain.receiveamount;

import java.util.Date;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.rea.infrastructure.model.Account;
import com.sotatek.rea.infrastructure.model.AccountHistory;
import com.sotatek.rea.infrastructure.model.Retail;
import com.sotatek.rea.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.rea.infrastructure.repository.AccountRepository;
import com.sotatek.rea.infrastructure.repository.RetailRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReceiveAmountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	@Autowired
	private RetailRepository retailRepository;
	
	public AccountHistory receiveAmount(Long retailId, Long amount, Long orderId) {
		try {
			Retail retail = retailRepository.findById(retailId).get();
			if(retail == null) {
				throw new AccountNotFoundException();
			}
			
			Account account = accountRepository.findByRetailId(retailId);
			if(account == null) {
				account = new Account();
				account.balance = 0L;
				account.retail = retail;
				accountRepository.save(account);
			}
			account.balance = account.balance + amount;
			accountRepository.save(account);
			
			AccountHistory accountHistory = new AccountHistory();
			accountHistory.amount = amount;
			accountHistory.createTime = new Date();
			accountHistory.retail = retail;
			accountHistory.orderId = orderId;
			accountHistory.type = "receive";
			accountHistoryRepository.save(accountHistory);
			return accountHistory;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
