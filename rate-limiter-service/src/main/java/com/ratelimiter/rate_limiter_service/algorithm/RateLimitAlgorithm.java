package com.ratelimiter.rate_limiter_service.algorithm;

public interface RateLimitAlgorithm {
    boolean isAllowed(String key, int requestLimit, int windowSizeInSeconds);

    long getRemainingRequests(String key, int requestLimit, int windowSizeInSeconds);

    long getResetTimeInSeconds(String key, int windowSizeInSeconds);
}