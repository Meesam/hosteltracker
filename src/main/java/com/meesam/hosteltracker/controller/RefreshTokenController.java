package com.meesam.hosteltracker.controller;

import com.meesam.hosteltracker.dto.AuthResponse;
import com.meesam.hosteltracker.dto.RefreshTokenRequest;
import com.meesam.hosteltracker.dto.UserResponse;
import com.meesam.hosteltracker.model.User;
import com.meesam.hosteltracker.security.JwtService;
import com.meesam.hosteltracker.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/refresh")
@RequiredArgsConstructor
public class RefreshTokenController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<?> getNewTokenFromRefreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String phone = jwtService.extractUsername(refreshTokenRequest.tokenHash());
        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        if (jwtService.isTokenExpired(refreshTokenRequest.tokenHash())) {
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
                RefreshTokenRequest refTokenRequest = new RefreshTokenRequest(
                        refreshToken, userEntity.getId(), userEntity.getPhone()
                );
                refreshTokenService.saveRefreshToken(refTokenRequest);

                UserResponse user = new UserResponse(
                        userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getPhone(),
                        userEntity.getEmail(),
                        userEntity.getDob(),
                        userEntity.getProfilePicturePath(),
                        userEntity.getLastLoginAt()
                );
                return new ResponseEntity<>(new AuthResponse(token, refreshToken, user), HttpStatus.OK);
            }
        } else {
            String token = jwtService.generateToken(userDetails);
            if (userDetails instanceof User userEntity) {
                UserResponse user = new UserResponse(
                        userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getPhone(),
                        userEntity.getEmail(),
                        userEntity.getDob(),
                        userEntity.getProfilePicturePath(),
                        userEntity.getLastLoginAt()
                );
                return new ResponseEntity<>(new AuthResponse(token, refreshTokenRequest.tokenHash(), user), HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "No record found"));
    }
}
