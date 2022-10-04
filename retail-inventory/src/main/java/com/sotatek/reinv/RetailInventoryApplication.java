package com.sotatek.reinv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringBootApplication
@SpringCloudApplication
public class RetailInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailInventoryApplication.class, args);
	}

}
