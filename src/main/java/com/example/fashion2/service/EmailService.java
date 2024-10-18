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
        message.setSubject("Подписка на рассылку");
        message.setText("Здравствуйте, " + name + "!\n\nСпасибо за подписку на нашу рассылку. Вы получите 30% скидку на следующую покупку.");
        mailSender.send(message);
    }
}