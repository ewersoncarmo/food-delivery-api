package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityModelRequest {

	@NotBlank
	@Size(max = 80)
	private String name;
	
	@Valid
	@NotNull
	private StateIdModelRequest state;
}
