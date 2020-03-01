package com.smartcook.fooddeliveryapi.domain.model.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantModelRequest {

	@NotBlank
	@Size(max = 80)
	private String name;
	
	@NotNull
	@Digits(integer = 19, fraction = 2)
	private BigDecimal freightRate;
	
	@Valid
	@NotNull
	private CuisineIdModelRequest cuisine;
	
	@Valid
	@NotNull
	private AddressModelRequest address;
}
