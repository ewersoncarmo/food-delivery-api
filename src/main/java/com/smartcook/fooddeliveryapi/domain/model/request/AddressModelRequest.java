package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModelRequest {

	@NotBlank
	@Size(max = 8)
	private String zipCode;
	
	@NotBlank
	@Size(max = 80)
	private String street;
	
	@NotBlank
	@Size(max = 10)
	private String number;
	
	@Size(max = 100)
	private String complement;
	
	@NotBlank
	@Size(max = 80)
	private String neighborhood;

	@Valid
	@NotNull
	private CityIdModelRequest city;
}
