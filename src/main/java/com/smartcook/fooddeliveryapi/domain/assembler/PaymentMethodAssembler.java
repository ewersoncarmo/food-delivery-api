package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;
import com.smartcook.fooddeliveryapi.domain.model.request.PaymentMethodModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.PaymentMethodModelResponse;

@Component
public class PaymentMethodAssembler extends AbstractAssembler<PaymentMethod, PaymentMethodModelRequest, PaymentMethodModelResponse> {

	@Override
	public PaymentMethod toEntity(PaymentMethodModelRequest request) {
		return modelMapper.map(request, PaymentMethod.class);
	}
	
	@Override
	public PaymentMethodModelResponse toModel(PaymentMethod entity) {
		return modelMapper.map(entity, PaymentMethodModelResponse.class);
	}
	
	@Override
	public List<PaymentMethodModelResponse> toCollectionModel(List<PaymentMethod> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}
	
	@Override
	public void copyToEntity(PaymentMethodModelRequest request, PaymentMethod entity) {
		modelMapper.map(request, entity);
	}
}
