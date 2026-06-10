package com.ratelimiter.analytics_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsResponseDTO {
    private String apiKey;
    private long totalRequests;
    private long allowedRequests;
    private long blockedRequests;
    private double blockRate;
}