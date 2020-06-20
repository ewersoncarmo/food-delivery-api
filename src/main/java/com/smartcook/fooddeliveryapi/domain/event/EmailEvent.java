package com.smartcook.fooddeliveryapi.domain.event;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class EmailEvent {

	private PurchaseOrder purchaseOrder;

}
