package com.smartcook.fooddeliveryapi.domain.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModelResponse {

	private String zipCode;
	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private CityModelResponse city;
}
