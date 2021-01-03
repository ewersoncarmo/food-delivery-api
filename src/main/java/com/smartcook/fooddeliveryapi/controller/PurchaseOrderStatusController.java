package com.smartcook.fooddeliveryapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcook.fooddeliveryapi.controller.security.CheckSecurity;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.service.PurchaseOrderService;
import com.smartcook.fooddeliveryapi.service.PurchaseOrderStatusService;

@RestController
@RequestMapping("/api/v1/purchase-orders/{purchaseOrderId}")
public class PurchaseOrderStatusController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private PurchaseOrderStatusService puchaseOrderStatusService;
	
	@CheckSecurity.PurchaseOrders.CanManage
	@PutMapping("/confirm")
	public ResponseEntity<Void> confirm(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
		PurchaseOrder purchaseOrder = purchaseOrderService.findById(purchaseOrderId);
		
		puchaseOrderStatusService.confirm(purchaseOrder);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.PurchaseOrders.CanManage
	@PutMapping("/deliver")
	public ResponseEntity<Void> deliver(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
		PurchaseOrder purchaseOrder = purchaseOrderService.findById(purchaseOrderId);
		
		puchaseOrderStatusService.deliver(purchaseOrder);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.PurchaseOrders.CanManage
	@PutMapping("/cancel")
	public ResponseEntity<Void> cancel(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
		PurchaseOrder purchaseOrder = purchaseOrderService.findById(purchaseOrderId);
		
		puchaseOrderStatusService.cancel(purchaseOrder);
		
		return ResponseEntity.noContent()
				.build();
	}
	
}
