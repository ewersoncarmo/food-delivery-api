package com.smartcook.fooddeliveryapi.domain.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityModelResponse {

	private Long id;
	private String name;
	private StateModelResponse state;
}
