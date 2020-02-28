package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.model.request.CuisineModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CuisineModelResponse;

@Component
public class CuisineAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cuisine toEntity(CuisineModelRequest cuisineModelRequest) {
		return modelMapper.map(cuisineModelRequest, Cuisine.class);
	}
	
	public CuisineModelResponse toModel(Cuisine cuisine) {
		return modelMapper.map(cuisine, CuisineModelResponse.class);
	}
	
	public List<CuisineModelResponse> toCollectionModel(List<Cuisine> cuisines) {
		return cuisines.stream()
				.map(cuisine -> toModel(cuisine))
				.collect(Collectors.toList());
	}
	
	public void copyToEntity(CuisineModelRequest cuisineModelRequest, Cuisine cuisine) {
		modelMapper.map(cuisineModelRequest, cuisine);
	}
}
