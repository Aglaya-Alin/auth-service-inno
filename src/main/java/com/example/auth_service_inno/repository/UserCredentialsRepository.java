package com.example.auth_service_inno.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.auth_service_inno.api.model.Role;
import com.example.auth_service_inno.api.model.UserCredentials;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {
    Optional<UserCredentials> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserCredentials> findAllByRole(Role role);
}
