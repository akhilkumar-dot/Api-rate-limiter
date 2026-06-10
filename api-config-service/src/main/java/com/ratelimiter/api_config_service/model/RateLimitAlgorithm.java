package com.ratelimiter.api_config_service.model;

public enum RateLimitAlgorithm {
    TOKEN_BUCKET,
    LEAKY_BUCKET,
    FIXED_WINDOW,
    SLIDING_WINDOW_LOG,
    SLIDING_WINDOW_COUNTER
}
