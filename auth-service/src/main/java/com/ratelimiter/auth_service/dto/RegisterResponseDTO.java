package com.ratelimiter.auth_service.dto;

import lombok.Data;

@Data
public class RegisterResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String companyName;
    private String status;

}
