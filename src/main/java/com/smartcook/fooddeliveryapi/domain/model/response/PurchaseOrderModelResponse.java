package com.smartcook.fooddeliveryapi.domain.model.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(value = Include.NON_NULL)
public class PurchaseOrderModelResponse {

	private Long id;
	private BigDecimal subTotal;
	private BigDecimal freightRate;
	private BigDecimal amount;
	private String orderStatus;
	private OffsetDateTime creationDate;
	private OffsetDateTime confirmationDate;
	private OffsetDateTime deliveryDate;
	private OffsetDateTime cancellationDate;
	private RestaurantSummaryModelResponse restaurant;
	private UserModelResponse user;
	private PaymentMethodModelResponse paymentMethod;
	private AddressModelResponse address;
	private List<PurchaseOrderItemModelResponse> items;
	
}
