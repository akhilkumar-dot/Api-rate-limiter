package com.ratelimiter.rate_limiter_service.client;

import com.ratelimiter.rate_limiter_service.dto.ApiConfigDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-config-service", url = "http://localhost:8082")
public interface ApiConfigClient {

    @GetMapping("/api/configs/key/{apiKey}")
    ApiConfigDTO getConfigByApiKey(@PathVariable String apiKey);
}