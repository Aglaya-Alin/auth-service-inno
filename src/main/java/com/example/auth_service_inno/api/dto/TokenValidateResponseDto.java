package com.example.auth_service_inno.api.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenValidateResponseDto {
    private boolean valid;
    private UUID userId;
    private String email;
    private String role;
}
