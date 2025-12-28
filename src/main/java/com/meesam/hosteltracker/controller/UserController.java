package com.meesam.hosteltracker.controller;

import com.meesam.hosteltracker.dto.UserRequest;
import com.meesam.hosteltracker.dto.UserResponse;
import com.meesam.hosteltracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest){
        return new  ResponseEntity<>(userService.createUser(userRequest),HttpStatus.CREATED);
    }
}
