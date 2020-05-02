package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.domain.model.request.CityModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CityModelResponse;

@Component
public class CityAssembler extends AbstractAssembler<City, CityModelRequest, CityModelResponse> {

	@Override
	public City toEntity(CityModelRequest request) {
		return modelMapper.map(request, City.class);
	}

	@Override
	public CityModelResponse toModel(City entity) {
		return modelMapper.map(entity, CityModelResponse.class);
	}

	@Override
	public List<CityModelResponse> toCollectionModel(List<City> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}

	@Override
	public void copyToEntity(CityModelRequest request, City entity) {
		entity.setState(new State());
		
		modelMapper.map(request, entity);
	}

}
