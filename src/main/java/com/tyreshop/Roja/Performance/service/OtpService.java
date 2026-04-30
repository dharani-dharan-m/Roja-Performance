package com.tyreshop.Roja.Performance.service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    private static final long OTP_TTL_MILLIS = 5 * 60 * 1000;
    private static final long VERIFIED_TTL_MILLIS = 10 * 60 * 1000;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final Map<String, OtpRecord> otpStore = new ConcurrentHashMap<>();
    private final Map<String, VerifiedRecord> verifiedStore = new ConcurrentHashMap<>();

    public OtpIssueResult issueOtp(String email, String purpose) {
        cleanupExpired();
        String key = key(email, purpose);
        String code = String.format("%06d", RANDOM.nextInt(1_000_000));
        otpStore.put(key, new OtpRecord(code, System.currentTimeMillis() + OTP_TTL_MILLIS));
        return new OtpIssueResult(code, OTP_TTL_MILLIS / 1000);
    }

    public OtpVerifyResult verifyOtp(String email, String purpose, String otpCode) {
        cleanupExpired();
        String key = key(email, purpose);
        OtpRecord record = otpStore.get(key);
        if (record == null) {
            return new OtpVerifyResult(false, "OTP not requested or expired", null, 0);
        }
        if (System.currentTimeMillis() > record.expiresAt) {
            otpStore.remove(key);
            return new OtpVerifyResult(false, "OTP expired", null, 0);
        }
        if (!record.code.equals(otpCode)) {
            return new OtpVerifyResult(false, "Invalid OTP code", null, 0);
        }
        otpStore.remove(key);
        String verificationToken = UUID.randomUUID().toString();
        verifiedStore.put(verificationToken, new VerifiedRecord(key, System.currentTimeMillis() + VERIFIED_TTL_MILLIS));
        return new OtpVerifyResult(true, "OTP verified", verificationToken, VERIFIED_TTL_MILLIS / 1000);
    }

    public boolean isVerified(String email, String purpose, String verificationToken) {
        cleanupExpired();
        VerifiedRecord record = verifiedStore.get(verificationToken);
        if (record == null) {
            return false;
        }
        if (System.currentTimeMillis() > record.expiresAt) {
            verifiedStore.remove(verificationToken);
            return false;
        }
        return record.key.equals(key(email, purpose));
    }

    private void cleanupExpired() {
        long now = System.currentTimeMillis();
        otpStore.entrySet().removeIf(entry -> entry.getValue().expiresAt <= now);
        verifiedStore.entrySet().removeIf(entry -> entry.getValue().expiresAt <= now);
    }

    private String key(String email, String purpose) {
        return email.trim().toLowerCase() + "|" + purpose.trim().toUpperCase();
    }

    private record OtpRecord(String code, long expiresAt) {}

    private record VerifiedRecord(String key, long expiresAt) {}

    public record OtpIssueResult(String otpCode, long expiresInSeconds) {}

    public record OtpVerifyResult(boolean success, String message, String verificationToken, long expiresInSeconds) {}
}
