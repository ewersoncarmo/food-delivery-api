package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.domain.model.request.CityModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CityModelResponse;

@Component
public class CityAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public City toEntity(CityModelRequest cityModelRequest) {
		return modelMapper.map(cityModelRequest, City.class);
	}
	
	public CityModelResponse toModel(City city) {
		return modelMapper.map(city, CityModelResponse.class);
	}
	
	public List<CityModelResponse> toCollectionModel(List<City> cities) {
		return cities.stream()
				.map(city -> toModel(city))
				.collect(Collectors.toList());
	}
	
	public void copyToEntity(CityModelRequest cityModelRequest, City city) {
		city.setState(new State());
		
		modelMapper.map(cityModelRequest, city);
	}
}
