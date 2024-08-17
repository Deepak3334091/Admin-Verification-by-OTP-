package com.Otp.Otp.Dao;

import com.Otp.Otp.Entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<Login,Long> {
}
