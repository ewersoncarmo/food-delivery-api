package com.smartcook.fooddeliveryapi.domain.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderModelRequest {

	@Valid
	@NotNull
	private RestaurantIdModelRequest restaurant;
	
	@Valid
	@NotNull
	private PaymentMethodIdModelRequest paymentMethod;
	
	@Valid
	@NotNull
	private AddressModelRequest address;

	@Valid
	@NotNull
	@Size(min = 1)
	private List<PurchaseOrderItemModelRequest> items;
	
}
