package com.ratelimiter.auth_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "developers")

public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String companyName;

    @Enumerated(EnumType.STRING)
    private DeveloperStatus status;

}