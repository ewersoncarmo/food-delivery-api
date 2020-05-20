package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.response.PurchaseOrderSummaryModelResponse;

@Component
public class PurchaseOrderSummaryAssembler {

	@Autowired
	protected ModelMapper modelMapper;
	
	public PurchaseOrderSummaryModelResponse toModel(PurchaseOrder entity) {
		return modelMapper.map(entity, PurchaseOrderSummaryModelResponse.class);
	}

	public List<PurchaseOrderSummaryModelResponse> toCollectionModel(List<PurchaseOrder> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}

}
