package com.smartcook.fooddeliveryapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;

@Service
public class PurchaseOrderStatusService {

	@Transactional
	public void confirm(PurchaseOrder purchaseOrder) {
		purchaseOrder.getOrderStatus().getPurchaseOrderStatusFlow().confirm(purchaseOrder);
	}

	@Transactional
	public void deliver(PurchaseOrder purchaseOrder) {
		purchaseOrder.getOrderStatus().getPurchaseOrderStatusFlow().deliver(purchaseOrder);
	}
	
	@Transactional
	public void cancel(PurchaseOrder purchaseOrder) {
		purchaseOrder.getOrderStatus().getPurchaseOrderStatusFlow().cancel(purchaseOrder);
	}

}
