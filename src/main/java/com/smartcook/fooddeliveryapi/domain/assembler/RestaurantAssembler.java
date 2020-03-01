package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.request.RestaurantModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.RestaurantModelResponse;

@Component
public class RestaurantAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurant toEntity(RestaurantModelRequest restaurantModelRequest) {
		return modelMapper.map(restaurantModelRequest, Restaurant.class);
	}
	
	public RestaurantModelResponse toModel(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantModelResponse.class);
	}
	
	public List<RestaurantModelResponse> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(restaurant -> toModel(restaurant))
				.collect(Collectors.toList());
	}
	
	public void copyToEntity(RestaurantModelRequest restaurantModelRequest, Restaurant restaurant) {
		restaurant.setCuisine(new Cuisine());
		restaurant.getAddress().setCity(new City());
		
		modelMapper.map(restaurantModelRequest, restaurant);
	}
}
