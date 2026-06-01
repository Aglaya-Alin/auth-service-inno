package com.example.auth_service_inno.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRefreshRequestDto {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
