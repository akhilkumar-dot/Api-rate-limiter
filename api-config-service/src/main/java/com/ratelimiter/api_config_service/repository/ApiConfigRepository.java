package com.ratelimiter.api_config_service.repository;

import com.ratelimiter.api_config_service.model.ApiConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiConfigRepository extends JpaRepository<ApiConfig, Long> {
    List<ApiConfig> findByDeveloperId(Long developerId);

    Optional<ApiConfig> findByApiKey(String apiKey);
}