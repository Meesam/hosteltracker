package com.meesam.hosteltracker.controller;

import com.meesam.hosteltracker.dto.*;
import com.meesam.hosteltracker.model.User;
import com.meesam.hosteltracker.security.JwtService;
import com.meesam.hosteltracker.service.OtpService;
import com.meesam.hosteltracker.service.RefreshTokenService;
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
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final OtpService otpService;
    private final RefreshTokenService refreshTokenService;

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
    public ResponseEntity<OtpResponse> generateOtp(@Valid @RequestBody LoginRequest request) {
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
    public ResponseEntity<?> login(@Valid @RequestBody OtpRequest request) {
        String phone = otpService.getMobileNumberFromOtp(request.getOtp());
        if (phone == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid or expired OTP. Please try again."));
        }

        //Fetch UserDetails to generate the token
        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

        // 3. Manually create the Authentication object
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        //Set it in the Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate the JWT
        String token = jwtService.generateToken(userDetails);

        // Get User info from userDetails
        if (userDetails instanceof User userEntity) {
            //Generate refresh token and save it to db and also send it in response
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(
                    refreshToken, userEntity.getId(), userEntity.getPhone()
            );
            refreshTokenService.saveRefreshToken(refreshTokenRequest);

            UserResponse user = new UserResponse(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getPhone(),
                    userEntity.getEmail(),
                    userEntity.getDob(),
                    userEntity.getProfilePicturePath(),
                    userEntity.getLastLoginAt(),
                    null
            );
            // 4. Return the Record as JSON
            return new ResponseEntity<>(new AuthResponse(token, refreshToken, user), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "No record found"));
    }

}
