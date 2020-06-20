package com.smartcook.fooddeliveryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.event.EmailEvent;

@Service
public class PurchaseOrderStatusService {

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Transactional
	public void confirm(PurchaseOrder purchaseOrder) {
		purchaseOrder.getOrderStatus().getPurchaseOrderStatusFlow().confirm(purchaseOrder);
		
		publichEmailEvent(purchaseOrder);
	}

	@Transactional
	public void deliver(PurchaseOrder purchaseOrder) {
		purchaseOrder.getOrderStatus().getPurchaseOrderStatusFlow().deliver(purchaseOrder);

		publichEmailEvent(purchaseOrder);
	}
	
	@Transactional
	public void cancel(PurchaseOrder purchaseOrder) {
		purchaseOrder.getOrderStatus().getPurchaseOrderStatusFlow().cancel(purchaseOrder);

		publichEmailEvent(purchaseOrder);
	}

	public void publichEmailEvent(PurchaseOrder purchaseOrder) {
		eventPublisher.publishEvent(new EmailEvent(purchaseOrder));
	}
}
