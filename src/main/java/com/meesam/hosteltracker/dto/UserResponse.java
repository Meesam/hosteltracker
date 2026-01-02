package com.meesam.hosteltracker.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String phone,
        String email,
        LocalDateTime dob,
        String profilePicturePath,
        //Boolean isActive,
        //Boolean isActivatedByOtp,
        //LocalDateTime createdAt,
        //LocalDateTime updatedAt,
        //String createdBy,
        //String lastModifiedBy,
        LocalDateTime lastLoginAt,
        List<UserAddressResponse> userAddressResponses
) {
    public Optional<LocalDateTime> getLastLoginAt() {
        return Optional.ofNullable(lastLoginAt);
    }

    public Optional<String> getProfilePicturePath(){
        return Optional.ofNullable(profilePicturePath);
    }


}
