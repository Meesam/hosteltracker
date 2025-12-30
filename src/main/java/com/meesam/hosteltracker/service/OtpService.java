package com.meesam.hosteltracker.service;

import com.meesam.hosteltracker.dto.OtpRequest;
import com.meesam.hosteltracker.dto.OtpResponse;
import com.meesam.hosteltracker.model.OtpTable;
import com.meesam.hosteltracker.repository.OtpRepository;
import com.meesam.hosteltracker.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;

    public OtpResponse saveOtp(OtpRequest otpRequest) {
        Optional<OtpTable> result = otpRepository.findByPhone(otpRequest.getPhone());
        if (result.isPresent()) {
            otpRepository.delete(result.get());
        }

        OtpTable otpTable = OtpTable.builder()
                .Otp(OtpGenerator.generateOtp())
                .userId(otpRequest.getUserId())
                .phone(otpRequest.getPhone())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();
        OtpTable savedOtp = otpRepository.save(otpTable);
        return OtpResponse.builder()
                .Otp(savedOtp.getOtp())
                .build();
    }

    public String getMobileNumberFromOtp(int otp) {
        Optional<OtpTable> otpTable = otpRepository.findByOtp(otp);
        if (otpTable.isPresent()) {
            OtpTable opt = otpTable.get();
            return opt.getPhone();
        } else {
            return null;
        }
    }
}
