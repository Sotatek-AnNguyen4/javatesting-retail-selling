package com.sotatek.rea.domain.settlement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.rea.infrastructure.model.AccountHistory;
import com.sotatek.rea.infrastructure.repository.AccountHistoryRepository;
import com.sotatek.rea.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SettlementService {

	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	public ResponseData<?> settlement() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
//			calendar.add(Calendar.DATE, -1);
			Date yesterday = calendar.getTime();
			return new ResponseData<List<SettlementDto>>(HttpStatus.OK, accountHistoryRepository.findByTypeAndCreateTime("receive", dateFormat.format(yesterday)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
}
