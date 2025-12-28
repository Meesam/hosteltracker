package com.meesam.hosteltracker.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String phone,
        String email,
        LocalDateTime dob,
        Boolean isActive,
        Boolean isActivatedByOtp,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String lastModifiedBy
) {
}
