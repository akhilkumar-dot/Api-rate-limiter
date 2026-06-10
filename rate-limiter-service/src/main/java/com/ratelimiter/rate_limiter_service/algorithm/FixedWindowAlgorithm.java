package com.ratelimiter.rate_limiter_service.algorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class FixedWindowAlgorithm implements RateLimitAlgorithm {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean isAllowed(String key, int requestLimit, int windowSizeInSeconds) {
        String redisKey = "fixed:" + key;

        Long count = redisTemplate.opsForValue().increment(redisKey);

        if (count == 1) {
            // First request — set expiry for the window
            redisTemplate.expire(redisKey, windowSizeInSeconds, TimeUnit.SECONDS);
        }

        return count <= requestLimit;
    }

    @Override
    public long getRemainingRequests(String key, int requestLimit, int windowSizeInSeconds) {
        String redisKey = "fixed:" + key;
        String value = redisTemplate.opsForValue().get(redisKey);
        long count = value != null ? Long.parseLong(value) : 0;
        return Math.max(0, requestLimit - count);
    }

    @Override
    public long getResetTimeInSeconds(String key, int windowSizeInSeconds) {
        String redisKey = "fixed:" + key;
        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        return ttl != null && ttl > 0 ? ttl : windowSizeInSeconds;
    }
}