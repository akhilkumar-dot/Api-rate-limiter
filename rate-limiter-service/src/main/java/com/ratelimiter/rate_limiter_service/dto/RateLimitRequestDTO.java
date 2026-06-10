package com.ratelimiter.rate_limiter_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RateLimitRequestDTO {

    @NotBlank(message = "API key is required")
    private String apiKey;

    @NotBlank(message = "Client ID is required")
    private String clientId;
}