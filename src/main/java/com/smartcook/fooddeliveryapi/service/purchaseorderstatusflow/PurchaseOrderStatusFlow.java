package com.smartcook.fooddeliveryapi.service.purchaseorderstatusflow;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;

public interface PurchaseOrderStatusFlow {

	void confirm(PurchaseOrder purchaseOrder);
	
	void deliver(PurchaseOrder purchaseOrder);
	
	void cancel(PurchaseOrder purchaseOrder);
}
