package com.ratelimiter.api_config_service.dto;

import com.ratelimiter.api_config_service.model.RateLimitAlgorithm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ApiConfigRequestDTO {

    @NotNull(message = "Developer ID is required")
    private Long developerId;

    @NotBlank(message = "API name is required")
    private String apiName;

    private String apiDescription;

    @NotNull(message = "Algorithm is required")
    private RateLimitAlgorithm algorithm;

    @NotNull(message = "Request limit is required")
    @Positive(message = "Request limit must be greater than 0")
    private Integer requestLimit;

    @NotNull(message = "Window size is required")
    @Positive(message = "Window size must be greater than 0")
    private Integer windowSizeInSeconds;
}