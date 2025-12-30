package com.meesam.hosteltracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "User";

    @Column(nullable = true)
    private LocalDateTime dob;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private  Boolean isActivatedByOtp = false;

    @Column(nullable = true)
    private LocalDateTime lastLoginAt;

}
