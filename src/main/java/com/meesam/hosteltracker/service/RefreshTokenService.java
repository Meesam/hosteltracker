package com.meesam.hosteltracker.service;

import com.meesam.hosteltracker.dto.RefreshTokenRequest;
import com.meesam.hosteltracker.model.RefreshTokens;
import com.meesam.hosteltracker.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;

  public void saveRefreshToken(RefreshTokenRequest request){
      Optional<RefreshTokens> refresh = refreshTokenRepository.findByPhone(request.phone());
      if(refresh.isPresent()){
          refreshTokenRepository.delete(refresh.get());
      }
      RefreshTokens refreshTokens = RefreshTokens.builder()
              .tokenHash(request.tokenHash())
              .phone(request.phone())
              .userId(request.userId())
              .createdAt(LocalDateTime.now())
              .expiresAt(LocalDateTime.now().plusDays(7))
              .build();
      refreshTokenRepository.save(refreshTokens);
  }
}
