package com.meesam.hosteltracker.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserAddressResponse {
    private UUID id;
    private String address;
    private String city;
    private String state;
    private String country;
    private int pinCode;
}
