package com.smartcook.fooddeliveryapi.domain.entity;

import com.smartcook.fooddeliveryapi.service.PurchaseOrderCanceledStatusFlow;
import com.smartcook.fooddeliveryapi.service.PurchaseOrderConfirmedStatusFlow;
import com.smartcook.fooddeliveryapi.service.PurchaseOrderCreatedStatusFlow;
import com.smartcook.fooddeliveryapi.service.PurchaseOrderDeliveredStatusFlow;
import com.smartcook.fooddeliveryapi.service.PurchaseOrderStatusFlow;

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
