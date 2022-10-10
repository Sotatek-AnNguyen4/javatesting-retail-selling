package com.sotatek.rea.domain.receiveamount;

import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.apache.http.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.rea.infrastructure.model.Account;
import com.sotatek.rea.infrastructure.model.AccountHistory;
import com.sotatek.rea.infrastructure.model.Retail;
import com.sotatek.rea.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.rea.infrastructure.repository.AccountRepository;
import com.sotatek.rea.infrastructure.repository.RetailRepository;
import com.sotatek.rea.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
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
	
	public ResponseData<?> receiveAmount(List<ReceiveAmountReqDto> request) {
		String result = "";
		for(ReceiveAmountReqDto receiveAmount: request) {
			try {
				Retail retail = retailRepository.findById(receiveAmount.retailId).get();
				if(retail == null) {
					throw new Exception("Retail "+ receiveAmount.retailId +" doesn't exist");
				}
				
				Account account = accountRepository.findByRetailId(receiveAmount.retailId);
				if(account == null) {
					account = new Account();
					account.balance = 0L;
					account.retail = retail;
					accountRepository.save(account);
				}
				account.balance = account.balance + receiveAmount.amount;
				accountRepository.save(account);
				
				AccountHistory accountHistory = new AccountHistory();
				accountHistory.amount = receiveAmount.amount;
				accountHistory.createTime = new Date();
				accountHistory.retail = retail;
				accountHistory.orderId = receiveAmount.orderId;
				accountHistory.type = "receive";
				accountHistoryRepository.save(accountHistory);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				result += e.getMessage() + ". ";
			}
		}
		if(result.isEmpty()) {
			return new ResponseData<String>(HttpStatus.OK, "Done");
		}
		return new ResponseData<String>(HttpStatus.BAD_REQUEST, result);
		
	}
}
