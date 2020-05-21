package com.smartcook.fooddeliveryapi.domain.model.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderFilter {

	private Long userId;
	private Long restaurantId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime creationDateBegin;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime creationDateEnd;
}
