package com.Otp.Otp.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name="logintable")
public class Login {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private LocalDateTime logintime;

    public Login() {
    }

    public Login(Long id, String email, LocalDateTime logintime) {
        this.id = id;
        this.email = email;
        this.logintime = logintime;
    }

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

    public LocalDateTime getLogintime() {
        return logintime;
    }

    public void setLogintime(LocalDateTime logintime) {
        this.logintime = logintime;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", logintime=" + logintime +
                '}';
    }
}
