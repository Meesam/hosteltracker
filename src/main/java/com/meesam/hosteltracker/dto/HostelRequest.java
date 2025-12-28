package com.meesam.hosteltracker.dto;

import jakarta.validation.constraints.NotBlank;

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

        @NotBlank(message = "Pin code is required")
        int pinCode,

        @NotBlank(message = "Contact person's name is required")
        String contactPerson,

        @NotBlank(message = "Contact person's number is required")
        String contactNumber
) {
}
