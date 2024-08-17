package com.Otp.Otp.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otptable")
public class OtpToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime savetime;

    // Default constructor (required by Hibernate/JPA)
    public OtpToken() {
    }

    // Constructor with fields (excluding id, assuming generated by DB)
    public OtpToken(String email, String otp, LocalDateTime savetime) {
        this.email = email;
        this.otp = otp;
        this.savetime = savetime;
    }

    // Getter and setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getSavetime() {
        return savetime;
    }

    public void setSavetime(LocalDateTime savetime) {
        this.savetime = savetime;
    }

    // Method to check if OTP is currently valid (assuming 2 minutes validity)
    public boolean isValid() {
        return savetime.plusMinutes(2).isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "OtpToken{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                ", savetime=" + savetime +
                '}';
    }
}
