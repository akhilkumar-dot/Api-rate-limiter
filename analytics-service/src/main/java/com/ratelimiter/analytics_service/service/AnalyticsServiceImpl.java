package com.ratelimiter.analytics_service.service;

import com.ratelimiter.analytics_service.dto.AnalyticsResponseDTO;
import com.ratelimiter.analytics_service.repository.RequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final RequestLogRepository requestLogRepository;

    @Override
    public AnalyticsResponseDTO getAnalyticsByApiKey(String apiKey) {
        long total = requestLogRepository.countByApiKey(apiKey);
        long allowed = requestLogRepository.countByApiKeyAndAllowed(apiKey, true);
        long blocked = requestLogRepository.countByApiKeyAndAllowed(apiKey, false);
        double blockRate = total > 0 ? (double) blocked / total * 100 : 0;

        return new AnalyticsResponseDTO(apiKey, total, allowed, blocked, blockRate);
    }

    @Override
    public List<AnalyticsResponseDTO> getAllAnalytics() {
        return requestLogRepository.findAll()
                .stream()
                .map(log -> log.getApiKey())
                .distinct()
                .map(this::getAnalyticsByApiKey)
                .collect(Collectors.toList());
    }
}