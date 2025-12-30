package com.meesam.hosteltracker.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HostelRequest(
        @NotBlank(message = "Hostel name is required")
        String name,

        String description,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Country is required")
        String country,

        @NotNull(message = "Pin code is required")
        @Min(value = 100000, message = "Pin code must be at least 6 digits")
        @Max(value = 999999, message = "Pin code cannot exceed 6 digits")
        int pinCode,

        @NotBlank(message = "Contact person's name is required")
        String contactPerson,

        @NotBlank(message = "Contact person's number is required")
        String contactNumber
) {
}
