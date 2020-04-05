package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;
import com.smartcook.fooddeliveryapi.domain.model.request.PaymentMethodModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.PaymentMethodModelResponse;

@Component
public class PaymentMethodAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PaymentMethod toEntity(PaymentMethodModelRequest paymentMethodModelRequest) {
		return modelMapper.map(paymentMethodModelRequest, PaymentMethod.class);
	}
	
	public PaymentMethodModelResponse toModel(PaymentMethod paymentMethod) {
		return modelMapper.map(paymentMethod, PaymentMethodModelResponse.class);
	}
	
	public List<PaymentMethodModelResponse> toCollectionModel(List<PaymentMethod> paymentMethods) {
		return paymentMethods.stream()
				.map(paymentMethod -> toModel(paymentMethod))
				.collect(Collectors.toList());
	}
	
	public void copyToEntity(PaymentMethodModelRequest paymentMethodModelRequest, PaymentMethod paymentMethod) {
		modelMapper.map(paymentMethodModelRequest, paymentMethod);
	}
}
