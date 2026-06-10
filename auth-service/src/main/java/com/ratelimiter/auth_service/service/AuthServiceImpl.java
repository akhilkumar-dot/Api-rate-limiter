package com.ratelimiter.auth_service.service;

import com.ratelimiter.auth_service.config.JwtUtil;
import com.ratelimiter.auth_service.dto.*;
import com.ratelimiter.auth_service.model.Developer;
import com.ratelimiter.auth_service.model.DeveloperStatus;
import com.ratelimiter.auth_service.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {

        // Check if email already exists
        if (developerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Developer developer = new Developer();
        developer.setName(requestDTO.getName());
        developer.setEmail(requestDTO.getEmail());
        developer.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        developer.setCompanyName(requestDTO.getCompanyName());
        developer.setStatus(DeveloperStatus.ACTIVE);

        Developer saved = developerRepository.save(developer);
        return mapToResponseDTO(saved);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {

        Developer developer = developerRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), developer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (developer.getStatus() == DeveloperStatus.SUSPENDED) {
            throw new RuntimeException("Your account has been suspended");
        }

        String token = jwtUtil.generateToken(developer.getEmail(), developer.getId());
        return new LoginResponseDTO(token, developer.getEmail(), developer.getCompanyName());
    }

    private RegisterResponseDTO mapToResponseDTO(Developer developer) {
        RegisterResponseDTO dto = new RegisterResponseDTO();
        dto.setId(developer.getId());
        dto.setName(developer.getName());
        dto.setEmail(developer.getEmail());
        dto.setCompanyName(developer.getCompanyName());
        dto.setStatus(developer.getStatus().name());
        return dto;
    }
}