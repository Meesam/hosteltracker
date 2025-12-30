package com.meesam.hosteltracker.repository;

import com.meesam.hosteltracker.model.OtpTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<OtpTable, UUID> {
    Optional<OtpTable> findByPhone(String phone);
    Optional<OtpTable> findByOtp(int otp);
}
