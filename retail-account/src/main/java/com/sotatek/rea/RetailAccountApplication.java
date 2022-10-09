package com.sotatek.rea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SpringCloudApplication
@EnableScheduling
public class RetailAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailAccountApplication.class, args);
	}

}
