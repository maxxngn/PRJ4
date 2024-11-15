package com.example.fashion2.service;

import com.example.fashion2.model.Subscriber;
import com.example.fashion2.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private EmailService emailService;
    public String saveSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
        
        String subject = "Congratulations on your successful registration!";
        String body = "Hello " + subscriber.getName() + ",\n\n"
                + "Thank you for registering to receive information from us. Congratulations on becoming an official member and you will receive a 30% discount on your order!";

        emailService.sendEmail(subscriber.getEmail(), subject, body);
        
        return "Congratulations on your successful registration!";
    }
}
