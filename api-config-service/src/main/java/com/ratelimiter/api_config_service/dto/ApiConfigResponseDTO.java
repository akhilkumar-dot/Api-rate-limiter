package com.ratelimiter.api_config_service.dto;

import com.ratelimiter.api_config_service.model.ApiStatus;
import com.ratelimiter.api_config_service.model.RateLimitAlgorithm;
import lombok.Data;

@Data
public class ApiConfigResponseDTO {
    private Long id;
    private Long developerId;
    private String apiName;
    private String apiDescription;
    private RateLimitAlgorithm algorithm;
    private Integer requestLimit;
    private Integer windowSizeInSeconds;
    private ApiStatus status;
    private String apiKey;
}