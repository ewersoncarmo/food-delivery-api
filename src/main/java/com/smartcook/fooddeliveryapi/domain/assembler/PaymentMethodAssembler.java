package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
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
		// add self relation
		PaymentMethodModelResponse paymentMethodResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, paymentMethodResponse);
		
		// add collection relation
		paymentMethodResponse.add(linkTo(PaymentMethodController.class).withRel("payment-methods"));
		
		return paymentMethodResponse;
	}
	
	@Override
	public CollectionModel<PaymentMethodModelResponse> toCollectionModel(Iterable<? extends PaymentMethod> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(PaymentMethodController.class).withSelfRel());
	}
	
	@Override
	public void copyToEntity(PaymentMethodModelRequest request, PaymentMethod entity) {
		modelMapper.map(request, entity);
	}
}
