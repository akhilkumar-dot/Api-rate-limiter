package com.ratelimiter.rate_limiter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class RateLimiterServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RateLimiterServiceApplication.class, args);
	}
}
