package com.ratelimiter.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
