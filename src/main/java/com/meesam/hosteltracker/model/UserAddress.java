package com.meesam.hosteltracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class UserAddress extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String state;

  @Column(nullable = false)
  private String country;

  @Column(nullable = false)
  private int pinCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id")
  private User users;

}
