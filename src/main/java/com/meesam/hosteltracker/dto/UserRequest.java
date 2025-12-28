package com.meesam.hosteltracker.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UserRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Phone is required")
        String phone,

        String email,
        LocalDateTime dob
) {
}
