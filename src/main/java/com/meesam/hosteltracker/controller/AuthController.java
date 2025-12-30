package com.meesam.hosteltracker.controller;

import com.meesam.hosteltracker.dto.*;
import com.meesam.hosteltracker.model.User;
import com.meesam.hosteltracker.security.JwtService;
import com.meesam.hosteltracker.service.OtpService;
import com.meesam.hosteltracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final OtpService otpService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/generateOtp")
    public ResponseEntity<OtpResponse> generateOtp(@RequestBody LoginRequest request) {
        Optional<User> userDetail = userService.findUserByPhoneNumber(request.phone());
        if (userDetail.isPresent()) {
            User user = userDetail.get();
            OtpRequest otpRequest = OtpRequest.builder()
                    .phone(user.getPhone())
                    .userId(user.getUserId())
                    .build();
            OtpResponse response = otpService.saveOtp(otpRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody OtpRequest request) {
        String mobileNumber = otpService.getMobileNumberFromOtp(request.getOtp());
        // 2. Fetch UserDetails to generate the token
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobileNumber);
        // 3. Manually create the Authentication object
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        // 4. Set it in the Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 3. Generate the JWT
        String token = jwtService.generateToken(userDetails);
        // 4. Return the Record as JSON
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
