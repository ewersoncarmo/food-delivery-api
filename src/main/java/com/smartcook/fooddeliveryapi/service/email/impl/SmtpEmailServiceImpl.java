package com.smartcook.fooddeliveryapi.service.email.impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.smartcook.fooddeliveryapi.configuration.EmailProperties;
import com.smartcook.fooddeliveryapi.domain.entity.OrderStatus;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.event.EmailEvent;
import com.smartcook.fooddeliveryapi.domain.model.dto.EmailMessage;
import com.smartcook.fooddeliveryapi.service.email.EmailService;
import com.smartcook.fooddeliveryapi.service.exception.EmailException;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpEmailServiceImpl implements EmailService {

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freeMarkerConfiguration;
	
	@Override
	public void send(EmailEvent event) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			PurchaseOrder purchaseOrder = event.getPurchaseOrder();

			String status = null;
			if (purchaseOrder.getOrderStatus().equals(OrderStatus.CREATED)) {
				status = "created. Wait for the confirmatio please.";
			} else if (purchaseOrder.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
				status = "confirmed and is being prepared.";
			} else if (purchaseOrder.getOrderStatus().equals(OrderStatus.DELIVERED)) {
				status = "delivery. Enjoy your meal.";
			} else if (purchaseOrder.getOrderStatus().equals(OrderStatus.CANCELED)) {
				status = "canceled. We hope you can come back soon.";
			}

			EmailMessage emailMessage = EmailMessage
				.builder()
					.recipient(purchaseOrder.getUser().getEmail())
					.subject(purchaseOrder.getRestaurant().getName() + " - Order " + purchaseOrder.getOrderStatus().getDescription())
					.body("purchase-order-status.html")
					.variable("purchaseOrder", purchaseOrder)
					.variable("status", status)
				.build();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom(emailProperties.getSender());
			helper.setTo(emailMessage.getRecipients().toArray(new String[0]));
			helper.setSubject(emailMessage.getSubject());
			helper.setText(processTemplate(emailMessage), true);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			LOG.error("There was an unexpected error while sending email.", e);
			throw new EmailException("There was an unexpected error while sending email.");
		}
	}

	private String processTemplate(EmailMessage emailMessage) {
		try {
			Template template = freeMarkerConfiguration.getTemplate(emailMessage.getBody());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, emailMessage.getVariables());
		} catch (Exception e) {
			LOG.error("There was an unexpected error while processing template before sending email.", e);
			throw new EmailException("There was an unexpected error while processing template before sending email.");
		}
	}
}
