package com.smartcook.fooddeliveryapi.service.email.impl;

import com.smartcook.fooddeliveryapi.domain.event.EmailEvent;
import com.smartcook.fooddeliveryapi.service.email.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("development")
public class LocalEmailServiceImpl implements EmailService {

    @Override
    public void send(EmailEvent event) {
        System.out.println("Sending e-mail " + event.getPurchaseOrder().getUser().getEmail());
    }
}
