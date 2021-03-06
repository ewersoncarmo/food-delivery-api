package com.smartcook.fooddeliveryapi.domain.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(value = Include.NON_NULL)
public class AddressModelResponse {

	private String zipCode;
	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private CityModelResponse city;
}
