package com.smartcook.fooddeliveryapi.domain.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodModelResponse extends RepresentationModel<PaymentMethodModelResponse> {

	private Long id;
	private String description;
}
