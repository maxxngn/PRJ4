package com.example.fashion2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
<<<<<<< HEAD
        message.setTo(toEmail);
        message.setSubject("Subscribe Confirm");
        message.setText("Hello, " + name + "!\n\nThank you for subscribing to our newsletter. You will receive a 30% discount on your next purchase.");
=======
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
>>>>>>> c4f17595ea197fad5a17148dbdeb9d6208cf080c
        mailSender.send(message);
    }
}
