package com.Otp.Otp.Dao;


import com.Otp.Otp.Entities.Admin;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface Adminrepo extends JpaRepository<Admin,Long> {

    Optional<Admin> findByEmail(String email);
}
