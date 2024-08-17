package com.Otp.Otp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
@Autowired
    private JavaMailSender javaMailSender;
    public void sendOtp(String toEmail,String otp){
try{
    SimpleMailMessage message=new SimpleMailMessage();
    message.setTo("deepakgosavi4091gi@gmail.com");
    message.setSubject("Password Reset Otp");
    message.setText("Your Otp For Password reset is:"+otp);
    javaMailSender.send(message);

}catch (Exception e){
    e.printStackTrace();
    throw new RuntimeException("Failed to send Otp vai email",e);
}
    }



}
