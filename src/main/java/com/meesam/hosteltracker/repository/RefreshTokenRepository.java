package com.meesam.hosteltracker.repository;

import com.meesam.hosteltracker.model.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokens, UUID> {
    Optional<RefreshTokens> findByPhone(String phone);
}
