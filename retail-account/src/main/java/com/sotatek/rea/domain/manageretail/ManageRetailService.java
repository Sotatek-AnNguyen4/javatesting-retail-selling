package com.sotatek.rea.domain.manageretail;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sotatek.rea.infrastructure.model.Retail;
import com.sotatek.rea.infrastructure.repository.RetailRepository;
import com.sotatek.rea.infrastructure.util.ResponseData;

import kong.unirest.HttpStatus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ManageRetailService {

	@Autowired
	private RetailRepository retailRepository;
	
	
	public ResponseData<?> add(Retail retail) {
		try {
			retail.token = org.apache.commons.codec.digest.DigestUtils.sha256Hex(retail.toString() + UUID.randomUUID().toString());
			retailRepository.save(retail);
			return new ResponseData<Retail>(HttpStatus.OK, retail);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	public ResponseData<?> update(Retail retail) {
		try {
			Retail retailOnDB = retailRepository.findById(retail.getId()).get();
			retailOnDB.email = retail.email;
			retailOnDB.name = retail.name;
			retailOnDB.phone = retail.phone;
			retailRepository.save(retailOnDB);
			return new ResponseData<Retail>(HttpStatus.OK, retailOnDB);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	public ResponseData<?> findByRetailId(Long retailId) {
		try {
			return new ResponseData<Retail>(HttpStatus.OK, retailRepository.findById(retailId).get());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseData<String>(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
}
