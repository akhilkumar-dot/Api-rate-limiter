package com.ratelimiter.auth_service.repository;

import com.ratelimiter.auth_service.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByEmail(String email);

    boolean existsByEmail(String email);
}