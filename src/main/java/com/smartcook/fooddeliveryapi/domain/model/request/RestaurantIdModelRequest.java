package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantIdModelRequest {

	@NotNull
	private Long id;
}
