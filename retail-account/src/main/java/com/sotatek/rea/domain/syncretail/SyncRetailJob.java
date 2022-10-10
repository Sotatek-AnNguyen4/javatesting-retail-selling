package com.sotatek.rea.domain.syncretail;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sotatek.rea.infrastructure.model.Retail;
import com.sotatek.rea.infrastructure.model.User;
import com.sotatek.rea.infrastructure.repository.RetailRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SyncRetailJob {
	
	@Autowired
	private RetailRepository retailRepository;
	
	@Autowired
    private RedisTemplate<Object, Object> redisTemplate;

	@Scheduled(fixedRate = 180000)
    @Transactional(readOnly = true)
    public void running() {
		ObjectMapper mapper = new ObjectMapper();  
		try (Stream<Retail> retailStream = retailRepository.getAllRetail()) {
            List<Retail> listRetail = retailStream.collect(Collectors.toList());
            listRetail.forEach(item -> {
				try {
					redisTemplate.opsForValue().set(item.token, mapper.writeValueAsString(new User(item.id, "retail")), 5, TimeUnit.MINUTES);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			});
        } catch (Exception e) {
            log.error("[SCHEDULE] " + e);
        }
	}
}
