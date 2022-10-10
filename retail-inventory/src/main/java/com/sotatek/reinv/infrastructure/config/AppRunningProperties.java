package com.sotatek.reinv.infrastructure.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Component
@Log4j2
@ConfigurationProperties(prefix = "spring.application")
public class AppRunningProperties {
    
    private String name;
    
    private String baseUrl;
    
}