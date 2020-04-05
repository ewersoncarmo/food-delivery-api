package com.smartcook.fooddeliveryapi.domain.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantModelResponse {

	private Long id;
	private String name;
	private BigDecimal freightRate;
	private CuisineModelResponse cuisine;
	private Boolean active;
	private AddressModelResponse address;
}
