package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodIdModelRequest {

	@NotNull
	private Long id;
}
