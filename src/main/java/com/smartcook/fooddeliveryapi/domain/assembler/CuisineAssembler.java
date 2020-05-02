package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.model.request.CuisineModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CuisineModelResponse;

@Component
public class CuisineAssembler extends AbstractAssembler<Cuisine, CuisineModelRequest, CuisineModelResponse> {

	@Override
	public Cuisine toEntity(CuisineModelRequest request) {
		return modelMapper.map(request, Cuisine.class);
	}
	
	@Override
	public CuisineModelResponse toModel(Cuisine entity) {
		return modelMapper.map(entity, CuisineModelResponse.class);
	}
	
	@Override
	public List<CuisineModelResponse> toCollectionModel(List<Cuisine> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}
	
	@Override
	public void copyToEntity(CuisineModelRequest request, Cuisine entity) {
		modelMapper.map(request, entity);
	}
}
