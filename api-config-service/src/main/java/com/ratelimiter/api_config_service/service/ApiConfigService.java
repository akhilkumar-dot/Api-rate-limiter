package com.ratelimiter.api_config_service.service;

import com.ratelimiter.api_config_service.dto.ApiConfigRequestDTO;
import com.ratelimiter.api_config_service.dto.ApiConfigResponseDTO;
import java.util.List;

public interface ApiConfigService {
    ApiConfigResponseDTO createConfig(ApiConfigRequestDTO requestDTO);

    ApiConfigResponseDTO getConfigById(Long id);

    ApiConfigResponseDTO getConfigByApiKey(String apiKey);

    List<ApiConfigResponseDTO> getConfigsByDeveloperId(Long developerId);

    ApiConfigResponseDTO updateConfig(Long id, ApiConfigRequestDTO requestDTO);

    void deleteConfig(Long id);
}