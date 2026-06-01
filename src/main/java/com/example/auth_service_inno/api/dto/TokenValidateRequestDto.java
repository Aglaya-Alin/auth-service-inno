package com.example.auth_service_inno.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenValidateRequestDto {
    
    @NotBlank(message = "Token is required")
    private String validateToken;
}
