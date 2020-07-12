package com.smartcook.fooddeliveryapi.domain.model.response;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantModelResponse extends RepresentationModel<RestaurantModelResponse> {

	private Long id;
	private String name;
	private BigDecimal freightRate;
	private CuisineModelResponse cuisine;
	private Boolean active;
	private Boolean open;
	private AddressModelResponse address;
}
