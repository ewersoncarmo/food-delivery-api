package com.smartcook.fooddeliveryapi.service.purchaseorderstatusflow;

import com.smartcook.fooddeliveryapi.domain.entity.OrderStatus;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

public class PurchaseOrderCreatedStatusFlow implements PurchaseOrderStatusFlow {

	@Override
	public void confirm(PurchaseOrder purchaseOrder) {
		purchaseOrder.confirm();
	}

	@Override
	public void deliver(PurchaseOrder purchaseOrder) {
		// M-27=It is not possible to change Purchase Order status from {0} to {1}.
		throw new ServiceException("M-27", purchaseOrder.getOrderStatus().getDescription(), OrderStatus.DELIVERED.getDescription());
	}

	@Override
	public void cancel(PurchaseOrder purchaseOrder) {
		purchaseOrder.cancel();
	}

}
