package com.meesam.hosteltracker.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record RefreshTokenRequest(
        @NotBlank(message = "Token can not be null or blank")
        String tokenHash,

        UUID userId,
        String phone
) {
}
