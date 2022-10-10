package com.sotatek.reinv.domain.settlement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sotatek.reinv.infrastructure.model.ProductHistory;
import com.sotatek.reinv.infrastructure.repository.ProductHistoryRepository;
import com.sotatek.reinv.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SettlementService {

	@Autowired
	private ProductHistoryRepository productHistoryRepository;

	public ResponseData<?> settlement() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
//			calendar.add(Calendar.DATE, -1);
			Date yesterday = calendar.getTime();
			return new ResponseData<List<SettlementResDto>>(HttpStatus.OK, productHistoryRepository.findByTypeAndCreateTime("buy", dateFormat.format(yesterday)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
}
