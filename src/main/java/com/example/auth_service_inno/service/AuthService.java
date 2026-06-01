package com.example.auth_service_inno.service;

import com.example.auth_service_inno.api.dto.LoginRequestDto;
import com.example.auth_service_inno.api.dto.RegisterRequestDto;
import com.example.auth_service_inno.api.dto.TokenRefreshRequestDto;
import com.example.auth_service_inno.api.dto.TokenResponseDto;
import com.example.auth_service_inno.api.dto.TokenValidateRequestDto;
import com.example.auth_service_inno.api.dto.TokenValidateResponseDto;
import com.example.auth_service_inno.api.model.UserCredentials;
import com.example.auth_service_inno.config.JwtTokenProvider;
import com.example.auth_service_inno.exception.UnauthorizedException;
import com.example.auth_service_inno.repository.UserCredentialsRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    
    private final UserCredentialsRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;


    public void register(RegisterRequestDto dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }
        UserCredentials user = UserCredentials.builder()
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();
        repository.save(user);
    }

    public TokenResponseDto login(LoginRequestDto dto) {
        UserCredentials user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid email or password");
        }
        String accessToken = tokenProvider.generateAccessToken(user.getId().toString(), user.getRole().name());
        String refreshToken = tokenProvider.generateRefreshToken(user.getId().toString());

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponseDto refreshToken(TokenRefreshRequestDto dto) {
        String incomingRefreshToken = dto.getRefreshToken();

        tokenProvider.validateToken(incomingRefreshToken);

        String userId = tokenProvider.getUserIdFromToken(incomingRefreshToken);

        UserCredentials user = repository.findById(java.util.UUID.fromString(userId))
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        String newAccessToken = tokenProvider.generateAccessToken(user.getId().toString(), user.getRole().name());
        String newRefreshToken = tokenProvider.generateRefreshToken(user.getId().toString());

        return TokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public TokenValidateResponseDto validateToken(TokenValidateRequestDto dto) {
        try {
            boolean isValid = tokenProvider.validateToken(dto.getValidateToken());
            if (isValid) {
                String userId = tokenProvider.getUserIdFromToken(dto.getValidateToken());
                String role = tokenProvider.getRoleFromToken(dto.getValidateToken());
                return TokenValidateResponseDto.builder()
                        .valid(true)
                        .userId(UUID.fromString(userId))
                        .role(role)
                        .build();
            }
        } catch (Exception e) {
        }
        return TokenValidateResponseDto.builder().valid(false).build();
    }
}
