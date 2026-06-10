package com.ratelimiter.rate_limiter_service.dto;

import lombok.Data;

@Data
public class ApiConfigDTO {
    private Long id;
    private Long developerId;
    private String apiName;
    private String algorithm;
    private Integer requestLimit;
    private Integer windowSizeInSeconds;
    private String status;
    private String apiKey;
}