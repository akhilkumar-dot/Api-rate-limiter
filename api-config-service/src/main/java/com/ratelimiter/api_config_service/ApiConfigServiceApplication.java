package com.ratelimiter.api_config_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConfigServiceApplication.class, args);
	}

}
