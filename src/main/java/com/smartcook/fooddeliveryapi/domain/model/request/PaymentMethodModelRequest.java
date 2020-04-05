package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodModelRequest {

	@NotBlank
	@Size(max = 40)
	private String description;
}
