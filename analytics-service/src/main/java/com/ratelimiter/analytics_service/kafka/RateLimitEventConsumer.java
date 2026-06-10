package com.ratelimiter.analytics_service.kafka;

import com.ratelimiter.analytics_service.dto.RateLimitEventDTO;
import com.ratelimiter.analytics_service.model.RequestLog;
import com.ratelimiter.analytics_service.repository.RequestLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitEventConsumer {

    private final RequestLogRepository requestLogRepository;

    @KafkaListener(topics = "rate-limit-events", groupId = "analytics-group")
    public void consumeEvent(RateLimitEventDTO event) {
        log.info("Received event for apiKey: {} allowed: {}",
                event.getApiKey(), event.isAllowed());

        RequestLog requestLog = new RequestLog();
        requestLog.setApiKey(event.getApiKey());
        requestLog.setClientId(event.getClientId());
        requestLog.setAllowed(event.isAllowed());
        requestLog.setAlgorithm(event.getAlgorithm());
        requestLog.setTimestamp(event.getTimestamp());

        requestLogRepository.save(requestLog);
        log.info("Saved request log for apiKey: {}", event.getApiKey());
    }
}