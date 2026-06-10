package com.ratelimiter.rate_limiter_service.algorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LeakyBucketAlgorithm implements RateLimitAlgorithm {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean isAllowed(String key, int requestLimit, int windowSizeInSeconds) {
        String bucketKey = "leaky:queue:" + key;
        String timeKey = "leaky:time:" + key;

        long now = System.currentTimeMillis() / 1000;
        String lastLeakStr = redisTemplate.opsForValue().get(timeKey);
        long lastLeak = lastLeakStr != null ? Long.parseLong(lastLeakStr) : now;

        // Calculate how many requests leaked out
        long elapsed = now - lastLeak;
        double leakRate = (double) requestLimit / windowSizeInSeconds;
        long leaked = (long) (elapsed * leakRate);

        // Get current queue size
        Long queueSize = redisTemplate.opsForList().size(bucketKey);
        if (queueSize == null)
            queueSize = 0L;

        // Drain leaked requests
        if (leaked > 0) {
            for (int i = 0; i < leaked && queueSize > 0; i++) {
                redisTemplate.opsForList().leftPop(bucketKey);
                queueSize--;
            }
            redisTemplate.opsForValue().set(timeKey, String.valueOf(now),
                    windowSizeInSeconds * 2L, TimeUnit.SECONDS);
        }

        // Check if bucket is full
        if (queueSize >= requestLimit) {
            return false;
        }

        // Add request to bucket
        redisTemplate.opsForList().rightPush(bucketKey, String.valueOf(now));
        redisTemplate.expire(bucketKey, windowSizeInSeconds * 2L, TimeUnit.SECONDS);

        return true;
    }

    @Override
    public long getRemainingRequests(String key, int requestLimit, int windowSizeInSeconds) {
        String bucketKey = "leaky:queue:" + key;
        Long size = redisTemplate.opsForList().size(bucketKey);
        return Math.max(0, requestLimit - (size != null ? size : 0));
    }

    @Override
    public long getResetTimeInSeconds(String key, int windowSizeInSeconds) {
        return windowSizeInSeconds;
    }
}