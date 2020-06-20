package com.smartcook.fooddeliveryapi.service.email;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.smartcook.fooddeliveryapi.domain.event.EmailEvent;

public interface EmailService {

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	void send(EmailEvent event);
	
}
