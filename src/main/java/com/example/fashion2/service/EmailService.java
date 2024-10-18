package com.example.fashion2.service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSubscriptionEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Subscribe Confirm");
        message.setText("Hello, " + name + "!\n\nThank you for subscribing to our newsletter. You will receive a 30% discount on your next purchase.");
        mailSender.send(message);
    }
}