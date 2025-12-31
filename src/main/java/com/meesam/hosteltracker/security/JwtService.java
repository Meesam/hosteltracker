package com.meesam.hosteltracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_STRING = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60; // 10 hours
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    public String generateToken(UserDetails userDetails) {
        return buildToken(userDetails, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, REFRESH_TOKEN_EXPIRATION);
    }

    private String buildToken(UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
            return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .verifyWith(key) // Replaces setSigningKey()
                    .build()         // Creates the parser
                    .parseSignedClaims(token) // Replaces parseClaimsJws()
                    .getPayload();   // Replaces getBody()
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return true; // Any other token error counts as expired/invalid
        }
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
