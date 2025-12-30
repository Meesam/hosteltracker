package com.meesam.hosteltracker.util;
import java.security.SecureRandom;

public class OtpGenerator {
    public static int generateOtp() {
        SecureRandom random = new SecureRandom();
        return 100_000 + random.nextInt(900_000);
    }
}
