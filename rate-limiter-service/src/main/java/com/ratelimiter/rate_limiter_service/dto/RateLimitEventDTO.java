package com.ratelimiter.rate_limiter_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitEventDTO {
    private String apiKey;
    private String clientId;
    private boolean allowed;
    private String algorithm;
    private long timestamp;
}