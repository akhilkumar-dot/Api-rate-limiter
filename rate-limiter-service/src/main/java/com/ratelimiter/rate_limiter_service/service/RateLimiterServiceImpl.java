package com.ratelimiter.rate_limiter_service.service;

import com.ratelimiter.rate_limiter_service.algorithm.*;
import com.ratelimiter.rate_limiter_service.client.ApiConfigClient;
import com.ratelimiter.rate_limiter_service.dto.*;
import com.ratelimiter.rate_limiter_service.kafka.RateLimitEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceImpl implements RateLimiterService {

    private final ApiConfigClient apiConfigClient;
    private final FixedWindowAlgorithm fixedWindowAlgorithm;
    private final SlidingWindowAlgorithm slidingWindowAlgorithm;
    private final TokenBucketAlgorithm tokenBucketAlgorithm;
    private final LeakyBucketAlgorithm leakyBucketAlgorithm;
    private final RateLimitEventProducer eventProducer;

    @Override
    public RateLimitResponseDTO checkRateLimit(RateLimitRequestDTO requestDTO) {

        ApiConfigDTO config = apiConfigClient.getConfigByApiKey(requestDTO.getApiKey());

        if (config == null) {
            throw new RuntimeException("Invalid API key");
        }

        if (!config.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("API is not active");
        }

        String redisKey = requestDTO.getApiKey() + ":" + requestDTO.getClientId();
        RateLimitAlgorithm algorithm = getAlgorithm(config.getAlgorithm());

        boolean allowed = algorithm.isAllowed(
                redisKey,
                config.getRequestLimit(),
                config.getWindowSizeInSeconds());

        long remaining = algorithm.getRemainingRequests(
                redisKey,
                config.getRequestLimit(),
                config.getWindowSizeInSeconds());

        long resetTime = algorithm.getResetTimeInSeconds(
                redisKey,
                config.getWindowSizeInSeconds());

        // Publish event to Kafka
        RateLimitEventDTO event = new RateLimitEventDTO(
                requestDTO.getApiKey(),
                requestDTO.getClientId(),
                allowed,
                config.getAlgorithm(),
                System.currentTimeMillis());
        eventProducer.publishEvent(event);

        if (allowed) {
            return new RateLimitResponseDTO(true, "Request allowed", remaining, resetTime);
        } else {
            return new RateLimitResponseDTO(false, "Rate limit exceeded. Try again later.", 0, resetTime);
        }
    }

    private RateLimitAlgorithm getAlgorithm(String algorithmName) {
        return switch (algorithmName) {
            case "FIXED_WINDOW" -> fixedWindowAlgorithm;
            case "SLIDING_WINDOW" -> slidingWindowAlgorithm;
            case "TOKEN_BUCKET" -> tokenBucketAlgorithm;
            case "LEAKY_BUCKET" -> leakyBucketAlgorithm;
            default -> throw new RuntimeException("Unknown algorithm: " + algorithmName);
        };
    }
}