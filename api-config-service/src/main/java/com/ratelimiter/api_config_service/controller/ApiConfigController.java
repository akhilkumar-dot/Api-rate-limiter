package com.ratelimiter.api_config_service.controller;

import com.ratelimiter.api_config_service.dto.ApiConfigRequestDTO;
import com.ratelimiter.api_config_service.dto.ApiConfigResponseDTO;
import com.ratelimiter.api_config_service.service.ApiConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configs")
@RequiredArgsConstructor
public class ApiConfigController {

    private final ApiConfigService apiConfigService;

    @PostMapping
    public ResponseEntity<ApiConfigResponseDTO> createConfig(
            @Valid @RequestBody ApiConfigRequestDTO requestDTO) {
        return ResponseEntity.ok(apiConfigService.createConfig(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiConfigResponseDTO> getConfigById(@PathVariable Long id) {
        return ResponseEntity.ok(apiConfigService.getConfigById(id));
    }

    @GetMapping("/key/{apiKey}")
    public ResponseEntity<ApiConfigResponseDTO> getConfigByApiKey(
            @PathVariable String apiKey) {
        return ResponseEntity.ok(apiConfigService.getConfigByApiKey(apiKey));
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<ApiConfigResponseDTO>> getConfigsByDeveloperId(
            @PathVariable Long developerId) {
        return ResponseEntity.ok(apiConfigService.getConfigsByDeveloperId(developerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiConfigResponseDTO> updateConfig(
            @PathVariable Long id,
            @Valid @RequestBody ApiConfigRequestDTO requestDTO) {
        return ResponseEntity.ok(apiConfigService.updateConfig(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable Long id) {
        apiConfigService.deleteConfig(id);
        return ResponseEntity.noContent().build();
    }
}