package com.ratelimiter.analytics_service.dto;

import lombok.Data;

@Data
public class RateLimitEventDTO {
    private String apiKey;
    private String clientId;
    private boolean allowed;
    private String algorithm;
    private long timestamp;
}