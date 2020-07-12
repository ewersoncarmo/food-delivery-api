package com.smartcook.fooddeliveryapi.domain.assembler;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.PaymentMethodController;
import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;
import com.smartcook.fooddeliveryapi.domain.model.request.PaymentMethodModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.PaymentMethodModelResponse;

@Component
public class PaymentMethodAssembler extends AbstractAssembler<PaymentMethod, PaymentMethodModelRequest, PaymentMethodModelResponse> {

	public PaymentMethodAssembler() {
		super(PaymentMethodController.class, PaymentMethodModelResponse.class);
	}

	@Override
	public PaymentMethod toEntity(PaymentMethodModelRequest request) {
		return modelMapper.map(request, PaymentMethod.class);
	}
	
	@Override
	public PaymentMethodModelResponse toModel(PaymentMethod entity) {
		return modelMapper.map(entity, PaymentMethodModelResponse.class);
	}
	
	@Override
	public void copyToEntity(PaymentMethodModelRequest request, PaymentMethod entity) {
		modelMapper.map(request, entity);
	}
}
