package com.smartcook.fooddeliveryapi.domain.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CuisineModelResponse extends RepresentationModel<CuisineModelResponse> {

	private Long id;
	private String name;
}
