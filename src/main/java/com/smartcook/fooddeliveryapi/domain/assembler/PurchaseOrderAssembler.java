package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.request.PurchaseOrderModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.PurchaseOrderModelResponse;

@Component
public class PurchaseOrderAssembler extends AbstractAssembler<PurchaseOrder, PurchaseOrderModelRequest, PurchaseOrderModelResponse> {

	@Override
	public PurchaseOrder toEntity(PurchaseOrderModelRequest request) {
		return modelMapper.map(request, PurchaseOrder.class);
	}

	@Override
	public PurchaseOrderModelResponse toModel(PurchaseOrder entity) {
		return modelMapper.map(entity, PurchaseOrderModelResponse.class);
	}

	@Override
	public List<PurchaseOrderModelResponse> toCollectionModel(List<PurchaseOrder> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}

	@Override
	public void copyToEntity(PurchaseOrderModelRequest request, PurchaseOrder entity) {
		modelMapper.map(request, entity);
	}

}
