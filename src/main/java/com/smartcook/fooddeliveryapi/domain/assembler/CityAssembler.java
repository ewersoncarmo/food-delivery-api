package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.CityController;
import com.smartcook.fooddeliveryapi.controller.StateController;
import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.domain.model.request.CityModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CityModelResponse;

@Component
public class CityAssembler extends AbstractAssembler<City, CityModelRequest, CityModelResponse> {

	public CityAssembler() {
		super(CityController.class, CityModelResponse.class);
	}

	@Override
	public City toEntity(CityModelRequest request) {
		return modelMapper.map(request, City.class);
	}

	@Override
	public CityModelResponse toModel(City entity) {
		// add self relation
		CityModelResponse cityModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, cityModelResponse);
		
		// add collection relation
		cityModelResponse.add(linkTo(CityController.class).withRel("cities"));
		// add self relation to state
		cityModelResponse.getState().add(linkTo(methodOn(StateController.class).findById(cityModelResponse.getState().getId())).withSelfRel());
		
		return cityModelResponse;
	}

	@Override
	public CollectionModel<CityModelResponse> toCollectionModel(Iterable<? extends City> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CityController.class).withSelfRel());
	}
	
	@Override
	public void copyToEntity(CityModelRequest request, City entity) {
		entity.setState(new State());
		
		modelMapper.map(request, entity);
	}

}
