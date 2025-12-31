package com.meesam.hosteltracker.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Phone number is required.")
        String phone
) {
}
