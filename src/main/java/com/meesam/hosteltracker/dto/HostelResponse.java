package com.meesam.hosteltracker.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class HostelResponse {
    private UUID id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String state;
    private String country;
    private int pinCode;
    private String contactPerson;
    private String contactNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;
}
