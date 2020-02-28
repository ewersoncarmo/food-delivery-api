package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CuisineModelRequest {

	@NotBlank
	@Size(max = 80)
	private String name;
}
