package com.smartcook.fooddeliveryapi.domain.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityModelResponse extends RepresentationModel<CityModelResponse> {

	private Long id;
	private String name;
	private StateModelResponse state;
}
