package com.ratelimiter.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password is cannot be empty")
    private String password;

    private String companyName;

}
