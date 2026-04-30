package com.tyreshop.Roja.Performance.controller;

import com.tyreshop.Roja.Performance.dto.ApiResponse;
import com.tyreshop.Roja.Performance.dto.OtpRequest;
import com.tyreshop.Roja.Performance.dto.OtpVerifyRequest;
import com.tyreshop.Roja.Performance.service.OtpService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<Map<String, Object>>> requestOtp(@Valid @RequestBody OtpRequest request) {
        OtpService.OtpIssueResult issueResult = otpService.issueOtp(request.getEmail(), request.getPurpose());
        Map<String, Object> data = new HashMap<>();
        data.put("expiresInSeconds", issueResult.expiresInSeconds());
        // Demo mode: expose OTP directly until email/SMS provider is integrated.
        data.put("otpCode", issueResult.otpCode());

        return ResponseEntity.ok(new ApiResponse<>(true, "OTP generated successfully", data));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Map<String, Object>>> verifyOtp(@Valid @RequestBody OtpVerifyRequest request) {
        OtpService.OtpVerifyResult result = otpService.verifyOtp(
            request.getEmail(),
            request.getPurpose(),
            request.getOtpCode()
        );
        if (!result.success()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, result.message(), null));
        }

        Map<String, Object> data = new HashMap<>();
        data.put("verificationToken", result.verificationToken());
        data.put("expiresInSeconds", result.expiresInSeconds());
        return ResponseEntity.ok(new ApiResponse<>(true, result.message(), data));
    }
}
