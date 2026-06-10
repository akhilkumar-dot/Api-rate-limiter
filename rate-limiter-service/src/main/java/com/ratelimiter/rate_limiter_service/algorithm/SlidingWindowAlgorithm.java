package com.ratelimiter.rate_limiter_service.algorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SlidingWindowAlgorithm implements RateLimitAlgorithm {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean isAllowed(String key, int requestLimit, int windowSizeInSeconds) {
        String redisKey = "sliding:" + key;
        long now = System.currentTimeMillis();
        long windowStart = now - (windowSizeInSeconds * 1000L);

        // Remove old requests outside the window
        redisTemplate.opsForZSet().removeRangeByScore(redisKey, 0, windowStart);

        // Count requests in current window
        Long count = redisTemplate.opsForZSet().zCard(redisKey);

        if (count != null && count >= requestLimit) {
            return false;
        }

        // Add current request with timestamp as score
        redisTemplate.opsForZSet().add(redisKey, String.valueOf(now), now);
        redisTemplate.expire(redisKey, windowSizeInSeconds, TimeUnit.SECONDS);

        return true;
    }

    @Override
    public long getRemainingRequests(String key, int requestLimit, int windowSizeInSeconds) {
        String redisKey = "sliding:" + key;
        long windowStart = System.currentTimeMillis() - (windowSizeInSeconds * 1000L);
        redisTemplate.opsForZSet().removeRangeByScore(redisKey, 0, windowStart);
        Long count = redisTemplate.opsForZSet().zCard(redisKey);
        return Math.max(0, requestLimit - (count != null ? count : 0));
    }

    @Override
    public long getResetTimeInSeconds(String key, int windowSizeInSeconds) {
        return windowSizeInSeconds;
    }
}