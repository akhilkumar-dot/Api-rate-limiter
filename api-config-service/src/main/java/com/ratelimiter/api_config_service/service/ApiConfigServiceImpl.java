package com.ratelimiter.api_config_service.service;

import com.ratelimiter.api_config_service.dto.ApiConfigRequestDTO;
import com.ratelimiter.api_config_service.dto.ApiConfigResponseDTO;
import com.ratelimiter.api_config_service.model.ApiConfig;
import com.ratelimiter.api_config_service.model.ApiStatus;
import com.ratelimiter.api_config_service.repository.ApiConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiConfigServiceImpl implements ApiConfigService {

    private final ApiConfigRepository apiConfigRepository;

    @Override
    public ApiConfigResponseDTO createConfig(ApiConfigRequestDTO requestDTO) {
        ApiConfig config = new ApiConfig();
        config.setDeveloperId(requestDTO.getDeveloperId());
        config.setApiName(requestDTO.getApiName());
        config.setApiDescription(requestDTO.getApiDescription());
        config.setAlgorithm(requestDTO.getAlgorithm());
        config.setRequestLimit(requestDTO.getRequestLimit());
        config.setWindowSizeInSeconds(requestDTO.getWindowSizeInSeconds());
        config.setStatus(ApiStatus.ACTIVE);

        // Auto generate unique API key
        config.setApiKey(UUID.randomUUID().toString());

        ApiConfig saved = apiConfigRepository.save(config);
        return mapToResponseDTO(saved);
    }

    @Override
    public ApiConfigResponseDTO getConfigById(Long id) {
        ApiConfig config = apiConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("API config not found with id: " + id));
        return mapToResponseDTO(config);
    }

    @Override
    public ApiConfigResponseDTO getConfigByApiKey(String apiKey) {
        ApiConfig config = apiConfigRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("API config not found for key: " + apiKey));
        return mapToResponseDTO(config);
    }

    @Override
    public List<ApiConfigResponseDTO> getConfigsByDeveloperId(Long developerId) {
        return apiConfigRepository.findByDeveloperId(developerId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApiConfigResponseDTO updateConfig(Long id, ApiConfigRequestDTO requestDTO) {
        ApiConfig config = apiConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("API config not found with id: " + id));

        config.setApiName(requestDTO.getApiName());
        config.setApiDescription(requestDTO.getApiDescription());
        config.setAlgorithm(requestDTO.getAlgorithm());
        config.setRequestLimit(requestDTO.getRequestLimit());
        config.setWindowSizeInSeconds(requestDTO.getWindowSizeInSeconds());

        return mapToResponseDTO(apiConfigRepository.save(config));
    }

    @Override
    public void deleteConfig(Long id) {
        apiConfigRepository.deleteById(id);
    }

    private ApiConfigResponseDTO mapToResponseDTO(ApiConfig config) {
        ApiConfigResponseDTO dto = new ApiConfigResponseDTO();
        dto.setId(config.getId());
        dto.setDeveloperId(config.getDeveloperId());
        dto.setApiName(config.getApiName());
        dto.setApiDescription(config.getApiDescription());
        dto.setAlgorithm(config.getAlgorithm());
        dto.setRequestLimit(config.getRequestLimit());
        dto.setWindowSizeInSeconds(config.getWindowSizeInSeconds());
        dto.setStatus(config.getStatus());
        dto.setApiKey(config.getApiKey());
        return dto;
    }
}