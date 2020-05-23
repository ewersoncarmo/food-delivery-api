package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.request.RestaurantModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.RestaurantModelResponse;

@Component
public class RestaurantAssembler extends AbstractAssembler<Restaurant, RestaurantModelRequest, RestaurantModelResponse> 
	implements PaginationAssembler<Restaurant, RestaurantModelResponse> {

	@Override
	public Restaurant toEntity(RestaurantModelRequest request) {
		return modelMapper.map(request, Restaurant.class);
	}
	
	@Override
	public RestaurantModelResponse toModel(Restaurant entity) {
		return modelMapper.map(entity, RestaurantModelResponse.class);
	}
	
	@Override
	public List<RestaurantModelResponse> toCollectionModel(List<Restaurant> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}
	
	@Override
	public void copyToEntity(RestaurantModelRequest request, Restaurant entity) {
		entity.setCuisine(new Cuisine());
		entity.getAddress().setCity(new City());
		
		modelMapper.map(request, entity);
	}

	@Override
	public Page<RestaurantModelResponse> toPageableModel(Pageable pageable, Page<Restaurant> page) {
		return new PageImpl<>(toCollectionModel(page.getContent()), pageable, page.getTotalElements());
	}
}
