package com.meesam.hosteltracker.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private final Long id;
    private final String fullName;
    private final String email;
}
