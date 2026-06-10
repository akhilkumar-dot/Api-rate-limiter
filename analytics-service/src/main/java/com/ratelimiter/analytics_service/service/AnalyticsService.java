package com.ratelimiter.analytics_service.service;

import com.ratelimiter.analytics_service.dto.AnalyticsResponseDTO;
import java.util.List;

public interface AnalyticsService {
    AnalyticsResponseDTO getAnalyticsByApiKey(String apiKey);

    List<AnalyticsResponseDTO> getAllAnalytics();
}