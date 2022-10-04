package com.sotatek.rea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringBootApplication
@SpringCloudApplication
public class RetailAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailAccountApplication.class, args);
	}

}
