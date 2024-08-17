package com.Otp.Otp.Controller;

import com.Otp.Otp.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        try {
            adminService.login(email, password);
            return ResponseEntity.ok("Login Successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/forget-pass")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");

        if (!isValidEmailFormat(email)) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        try {
            adminService.initiateForgotPassword(email);
            return ResponseEntity.ok("If the email exists in our records, an OTP has been sent to your email.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> requestBody) {
        String otp = requestBody.get("otp");

        boolean isValid = adminService.verifyOtp(otp);
        if (isValid) {
            return ResponseEntity.ok("OTP Verified");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP or expired");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String newPassword = requestBody.get("newPassword");
        String confirmPassword = requestBody.get("confirmPassword");

        try {
            adminService.resetPassword(email, newPassword, confirmPassword);
            return ResponseEntity.ok("Password has been reset");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean isValidEmailFormat(String email) {
        // Implement email format validation logic if needed
        // For simplicity, assuming basic check
        return email != null && email.contains("@");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
