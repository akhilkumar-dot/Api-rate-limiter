package com.ratelimiter.rate_limiter_service.kafka;

import com.ratelimiter.rate_limiter_service.dto.RateLimitEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitEventProducer {

    private static final String TOPIC = "rate-limit-events";
    private final KafkaTemplate<String, RateLimitEventDTO> kafkaTemplate;

    public void publishEvent(RateLimitEventDTO event) {
        kafkaTemplate.send(TOPIC, event.getApiKey(), event);
        log.info("Published rate limit event for apiKey: {}", event.getApiKey());
    }
}