package com.example.fashion2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Subscription Confirmation");
        message.setText(String.format("Hello, %s! You have successfully subscribed to the newsletter with a discount! Please check your email for confirmation.", name));
        mailSender.send(message);
    }
}