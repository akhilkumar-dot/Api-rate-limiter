package com.ratelimiter.analytics_service.controller;

import com.ratelimiter.analytics_service.dto.AnalyticsResponseDTO;
import com.ratelimiter.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{apiKey}")
    public ResponseEntity<AnalyticsResponseDTO> getAnalyticsByApiKey(
            @PathVariable String apiKey) {
        return ResponseEntity.ok(analyticsService.getAnalyticsByApiKey(apiKey));
    }

    @GetMapping
    public ResponseEntity<List<AnalyticsResponseDTO>> getAllAnalytics() {
        return ResponseEntity.ok(analyticsService.getAllAnalytics());
    }
}