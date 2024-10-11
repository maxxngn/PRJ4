package com.example.fashion2.service;

import com.example.fashion2.model.Subscriber;
import com.example.fashion2.repository.SubscriberRepository;
import com.example.fashion2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private EmailService emailService;

    public Subscriber subscribe(Subscriber subscriber) {
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        emailService.sendConfirmationEmail(savedSubscriber.getEmail(), savedSubscriber.getName());
        return savedSubscriber;
    }
}