package com.meesam.hosteltracker.repository;

import com.meesam.hosteltracker.model.OtpTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<OtpTable, UUID> {
    Optional<OtpTable> findByPhone(String phone);

    @Query("Select o from OtpTable o where o.otp =:otp AND o.expiresAt > :now")
    Optional<OtpTable> findByOtp(int otp, LocalDateTime now);
}
