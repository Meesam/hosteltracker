package com.meesam.hosteltracker.controller;

import com.meesam.hosteltracker.dto.UserAddressRequest;
import com.meesam.hosteltracker.dto.UserResponse;
import com.meesam.hosteltracker.service.UserAddressService;
import com.meesam.hosteltracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService userAddressService;
    private final UserService userService;

    @PostMapping("/addUserAddress")
    public ResponseEntity<UserResponse> addUserAddress(@Valid @RequestBody UserAddressRequest userAddressRequest) {
        return new ResponseEntity<>(userAddressService.addUserAddress(userAddressRequest), HttpStatus.CREATED);
    }

}
