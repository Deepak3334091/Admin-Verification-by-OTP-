package com.Otp.Otp.Dao;

import com.Otp.Otp.Entities.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpRepo extends JpaRepository<OtpToken,Long> {


    Optional<OtpToken> findByEmailAndOtp(String email, String otp);
    void deleteByEmail(String email);


    Optional<OtpToken> findByEmail(String email);
    Optional<OtpToken>findByOtp(String otp);
}
