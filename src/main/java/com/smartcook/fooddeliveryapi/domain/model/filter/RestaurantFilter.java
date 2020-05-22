package com.smartcook.fooddeliveryapi.domain.model.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantFilter {

	private Long cuisineId;
	private Long cityId;
	private Boolean active;
	private Boolean open;
}
