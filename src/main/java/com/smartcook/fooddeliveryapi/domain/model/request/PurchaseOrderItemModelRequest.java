package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderItemModelRequest {

	@NotNull
	private Long productId;
	
	@NotNull
	@Positive
	private Integer quantity;
	
	@Size(max = 255)
	private String note;
}
