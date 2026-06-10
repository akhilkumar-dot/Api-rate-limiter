package com.ratelimiter.rate_limiter_service.algorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean isAllowed(String key, int requestLimit, int windowSizeInSeconds) {
        String tokenKey = "token:tokens:" + key;
        String timeKey = "token:time:" + key;

        long now = System.currentTimeMillis() / 1000;

        String lastRefillStr = redisTemplate.opsForValue().get(timeKey);
        String tokensStr = redisTemplate.opsForValue().get(tokenKey);

        long tokens = tokensStr != null ? Long.parseLong(tokensStr) : requestLimit;
        long lastRefill = lastRefillStr != null ? Long.parseLong(lastRefillStr) : now;

        // Refill tokens based on time passed
        long elapsed = now - lastRefill;
        double refillRate = (double) requestLimit / windowSizeInSeconds;
        tokens = Math.min(requestLimit, tokens + (long) (elapsed * refillRate));

        if (tokens <= 0) {
            return false;
        }

        // Consume one token
        tokens--;
        redisTemplate.opsForValue().set(tokenKey, String.valueOf(tokens),
                windowSizeInSeconds * 2L, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(timeKey, String.valueOf(now),
                windowSizeInSeconds * 2L, TimeUnit.SECONDS);

        return true;
    }

    @Override
    public long getRemainingRequests(String key, int requestLimit, int windowSizeInSeconds) {
        String tokenKey = "token:tokens:" + key;
        String value = redisTemplate.opsForValue().get(tokenKey);
        return value != null ? Long.parseLong(value) : requestLimit;
    }

    @Override
    public long getResetTimeInSeconds(String key, int windowSizeInSeconds) {
        return windowSizeInSeconds;
    }
}