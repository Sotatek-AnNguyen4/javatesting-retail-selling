package com.sotatek.rea.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/health")
public class HealthCheckController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String healthy() {
        return "true";
    }

}
