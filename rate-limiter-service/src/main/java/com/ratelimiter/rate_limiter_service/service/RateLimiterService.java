package com.ratelimiter.rate_limiter_service.service;

import com.ratelimiter.rate_limiter_service.dto.RateLimitRequestDTO;
import com.ratelimiter.rate_limiter_service.dto.RateLimitResponseDTO;

public interface RateLimiterService {
    RateLimitResponseDTO checkRateLimit(RateLimitRequestDTO requestDTO);
}