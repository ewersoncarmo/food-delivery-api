package com.smartcook.fooddeliveryapi.domain.assembler;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.CuisineController;
import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.model.request.CuisineModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CuisineModelResponse;

@Component
public class CuisineAssembler extends AbstractAssembler<Cuisine, CuisineModelRequest, CuisineModelResponse> {

	public CuisineAssembler() {
		super(CuisineController.class, CuisineModelResponse.class);
	}

	@Override
	public Cuisine toEntity(CuisineModelRequest request) {
		return modelMapper.map(request, Cuisine.class);
	}
	
	@Override
	public CuisineModelResponse toModel(Cuisine entity) {
		return modelMapper.map(entity, CuisineModelResponse.class);
	}
	
	@Override
	public void copyToEntity(CuisineModelRequest request, Cuisine entity) {
		modelMapper.map(request, entity);
	}
}
