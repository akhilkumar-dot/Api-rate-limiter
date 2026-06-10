package com.ratelimiter.auth_service.service;

import com.ratelimiter.auth_service.dto.*;

public interface AuthService {
    RegisterResponseDTO register(RegisterRequestDTO requestDTO);

    LoginResponseDTO login(LoginRequestDTO requestDTO);
}