package com.meesam.hosteltracker.dto;

public record AuthResponse(String token, String refreshToken ,UserResponse user) {
}
