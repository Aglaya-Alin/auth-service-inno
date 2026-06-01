package com.example.auth_service_inno.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.auth_service_inno.api.dto.LoginRequestDto;
import com.example.auth_service_inno.api.dto.RegisterRequestDto;
import com.example.auth_service_inno.api.dto.TokenRefreshRequestDto;
import com.example.auth_service_inno.api.dto.TokenResponseDto;
import com.example.auth_service_inno.api.dto.TokenValidateRequestDto;
import com.example.auth_service_inno.api.dto.TokenValidateResponseDto;
import com.example.auth_service_inno.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerDto) {
        authService.register(registerDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        TokenResponseDto tokens = authService.login(loginDto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody TokenRefreshRequestDto refreshDto) {
        TokenResponseDto tokens = authService.refreshToken(refreshDto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenValidateResponseDto> validate(@RequestBody TokenValidateRequestDto validateDto) {
        TokenValidateResponseDto response = authService.validateToken(validateDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-credentials")
    public ResponseEntity<String> saveUserCredentials(@RequestBody RegisterRequestDto registerDto) {
        authService.register(registerDto);
        return new ResponseEntity<>("User credentials saved successfully", HttpStatus.CREATED);
    }
}
