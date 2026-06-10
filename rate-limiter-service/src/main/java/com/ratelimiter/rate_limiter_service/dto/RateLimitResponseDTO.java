package com.ratelimiter.rate_limiter_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateLimitResponseDTO {
    private boolean allowed;
    private String message;
    private long remainingRequests;
    private long resetTimeInSeconds;
}