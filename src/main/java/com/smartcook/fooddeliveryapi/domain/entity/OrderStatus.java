package com.smartcook.fooddeliveryapi.domain.entity;

import com.smartcook.fooddeliveryapi.service.purchaseorderstatusflow.PurchaseOrderCanceledStatusFlow;
import com.smartcook.fooddeliveryapi.service.purchaseorderstatusflow.PurchaseOrderConfirmedStatusFlow;
import com.smartcook.fooddeliveryapi.service.purchaseorderstatusflow.PurchaseOrderCreatedStatusFlow;
import com.smartcook.fooddeliveryapi.service.purchaseorderstatusflow.PurchaseOrderDeliveredStatusFlow;
import com.smartcook.fooddeliveryapi.service.purchaseorderstatusflow.PurchaseOrderStatusFlow;

public enum OrderStatus {

	CREATED("Created", new PurchaseOrderCreatedStatusFlow()),
	CONFIRMED("Confirmed", new PurchaseOrderConfirmedStatusFlow()),
	DELIVERED("Delivered", new PurchaseOrderDeliveredStatusFlow()),
	CANCELED("Canceled", new PurchaseOrderCanceledStatusFlow());
	
	private String description;
	private PurchaseOrderStatusFlow purchaseOrderStatusFlow;

	private OrderStatus(String description, PurchaseOrderStatusFlow purchaseOrderStatusFlow) {
		this.description = description;
		this.purchaseOrderStatusFlow = purchaseOrderStatusFlow;
	}
	
	public String getDescription() {
		return description;
	}
	
	public PurchaseOrderStatusFlow getPurchaseOrderStatusFlow() {
		return purchaseOrderStatusFlow;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
