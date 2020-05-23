package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.response.PurchaseOrderSummaryModelResponse;

@Component
public class PurchaseOrderSummaryAssembler implements PaginationAssembler<PurchaseOrder, PurchaseOrderSummaryModelResponse> {

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

	@Override
	public Page<PurchaseOrderSummaryModelResponse> toPageableModel(Pageable pageable, Page<PurchaseOrder> page) {
		return new PageImpl<>(toCollectionModel(page.getContent()), pageable, page.getTotalElements());
	}

}
