package com.Otp.Otp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailConfig {
@Bean
    public JavaMailSender javaMailSender(){
    JavaMailSenderImpl mailSender =new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername("deepakgosavi4091@gmail.com");
    mailSender.setPassword("oaqdhrzgagdcyhad");

    Properties pros=mailSender.getJavaMailProperties();
    pros.put("mail.trasport.protocol","smtp");
    pros.put("mail.smtp.auth","true");
    return mailSender;
}





}
