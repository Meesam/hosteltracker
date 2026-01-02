package com.meesam.hosteltracker.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserAddressRequest(
        @NotBlank(message = "Address is required")
        String address,
        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Country is required")
        String country,

        @NotNull(message = "Pin code is required")
        int pinCode,

        @NotBlank(message = "UserId is required")
        UUID userId

) {
}
