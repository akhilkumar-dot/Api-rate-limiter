package com.ratelimiter.rate_limiter_service.controller;

import com.ratelimiter.rate_limiter_service.dto.RateLimitRequestDTO;
import com.ratelimiter.rate_limiter_service.dto.RateLimitResponseDTO;
import com.ratelimiter.rate_limiter_service.service.RateLimiterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratelimit")
@RequiredArgsConstructor
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    @PostMapping("/check")
    public ResponseEntity<RateLimitResponseDTO> checkRateLimit(
            @Valid @RequestBody RateLimitRequestDTO requestDTO) {

        RateLimitResponseDTO response = rateLimiterService.checkRateLimit(requestDTO);

        // Return 429 if rate limit exceeded
        if (!response.isAllowed()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }

        return ResponseEntity.ok(response);
    }
}