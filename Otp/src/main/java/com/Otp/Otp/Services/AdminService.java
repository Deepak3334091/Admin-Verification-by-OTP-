package com.Otp.Otp.Services;

import com.Otp.Otp.Dao.Adminrepo;
import com.Otp.Otp.Dao.LoginRepo;
import com.Otp.Otp.Dao.OtpRepo;
import com.Otp.Otp.Entities.Admin;
import com.Otp.Otp.Entities.OtpToken;
//import com.Otp.Otp.EmailSender; // Assuming EmailSender is in this package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AdminService {

    private final Adminrepo adminrepo;
    private final LoginRepo loginRepo;
    private final OtpRepo otpRepo;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(Adminrepo adminrepo, LoginRepo loginRepo, OtpRepo otpRepo, EmailSender emailSender, PasswordEncoder passwordEncoder) {
        this.adminrepo = adminrepo;
        this.loginRepo = loginRepo;
        this.otpRepo = otpRepo;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void login(String email, String password) {
        Admin user = adminrepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public Optional<Admin> findByEmail(String email) {
        return adminrepo.findByEmail(email);
    }

    public String initiateForgotPassword(String email) {
        Optional<Admin> user = adminrepo.findByEmail(email);

        if (user.isPresent()) {
            Optional<OtpToken> existingOtpToken = otpRepo.findByEmail(email);
            String otp;

            if (existingOtpToken.isPresent() && existingOtpToken.get().getSavetime().isAfter(LocalDateTime.now().minusSeconds(60))) {
                otp = existingOtpToken.get().getOtp(); // Use existing OTP if it's still valid
            } else {
                otp = generateOtp(); // Generate new OTP
                OtpToken otpToken = existingOtpToken.orElse(new OtpToken());
                otpToken.setEmail(email);
                otpToken.setOtp(otp);
                otpToken.setSavetime(LocalDateTime.now());
                otpRepo.save(otpToken);
            }

            try {
                emailSender.sendOtp(email, otp);
            } catch (Exception e) {
                // Handle exception from email service
                throw new RuntimeException("Failed to send OTP via email", e);
            }
            return "OTP sent to your email. Please check your inbox.";
        } else {
            throw new RuntimeException("Email not found");
        }
    }

    @Transactional
    public boolean verifyOtp(String email, String otp) {
        Optional<OtpToken> otpToken = otpRepo.findByOtp(otp);

        if (otpToken.isPresent() && otpToken.get().getSavetime().isAfter(LocalDateTime.now().minusSeconds(120))) {
            otpRepo.deleteByEmail(email); // Invalidate the OTP after use
            return true;
        } else {
            System.out.println("OTP not found or incorrect");
            return false;
        }
    }

    public boolean verifyOtp(String otp) {
        // Retrieve the logged-in user's email from the security context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return verifyOtp(email, otp);
    }

    @Transactional
    public void resetPassword(String email, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        Optional<Admin> user = adminrepo.findByEmail(email);
        if (user.isPresent()) {
            Admin adminUser = user.get();
            adminUser.setPassword(passwordEncoder.encode(newPassword));
            adminrepo.save(adminUser);
        } else {
            throw new RuntimeException("Email not found");
        }
    }

    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
}
