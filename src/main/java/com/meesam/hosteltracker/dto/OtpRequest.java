package com.meesam.hosteltracker.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    @NotNull(message = "Otp is required")
    @Min(value = 100000, message = "Otp must be at least 6 digits")
    @Max(value = 999999, message = "Otp code cannot exceed 6 digits")
    private int otp;
    private UUID userId;
    private String phone;
}
