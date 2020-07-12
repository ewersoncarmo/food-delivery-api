package com.smartcook.fooddeliveryapi.domain.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionModelResponse extends RepresentationModel<PermissionModelResponse> {

	private Long id;
	private String name;
	private String description;
}
