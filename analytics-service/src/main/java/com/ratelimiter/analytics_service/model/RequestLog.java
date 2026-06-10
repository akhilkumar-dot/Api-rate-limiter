package com.ratelimiter.analytics_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "request_logs")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiKey;
    private String clientId;
    private boolean allowed;
    private String algorithm;
    private long timestamp;
}