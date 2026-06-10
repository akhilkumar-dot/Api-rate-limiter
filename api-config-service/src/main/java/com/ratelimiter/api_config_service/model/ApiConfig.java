package com.ratelimiter.api_config_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "api_configs")
public class ApiConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long developerId;

    private String apiName;
    private String apiDescription;
    @Enumerated(EnumType.STRING)
    private RateLimitAlgorithm algorithm;

    private Integer requestLimit;
    private Integer windowSizeInSeconds;

    @Enumerated(EnumType.STRING)
    private ApiStatus status;

    private String apiKey;

}
