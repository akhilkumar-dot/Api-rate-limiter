package com.ratelimiter.analytics_service.repository;

import com.ratelimiter.analytics_service.model.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
    List<RequestLog> findByApiKey(String apiKey);

    long countByApiKey(String apiKey);

    long countByApiKeyAndAllowed(String apiKey, boolean allowed);
}