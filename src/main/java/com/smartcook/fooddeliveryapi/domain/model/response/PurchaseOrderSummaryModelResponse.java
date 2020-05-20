package com.smartcook.fooddeliveryapi.domain.model.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderSummaryModelResponse {

	private Long id;
	private BigDecimal subTotal;
	private BigDecimal freightRate;
	private BigDecimal amount;
	private String orderStatus;
	private OffsetDateTime creationDate;
	private RestaurantSummaryModelResponse restaurant;
	private UserModelResponse user;
	
}
