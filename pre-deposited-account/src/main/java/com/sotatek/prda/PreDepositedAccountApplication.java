package com.sotatek.prda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SpringCloudApplication
@EnableScheduling
public class PreDepositedAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreDepositedAccountApplication.class, args);
	}

}
