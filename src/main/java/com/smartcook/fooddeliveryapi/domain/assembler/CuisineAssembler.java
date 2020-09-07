package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.CityController;
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
		// add link to self relation
		CuisineModelResponse cuisineModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, cuisineModelResponse);
		
		cuisineModelResponse.add(linkTo(CuisineController.class).withRel("cuisines"));
		
		return cuisineModelResponse;
	}
	
	@Override
	public CollectionModel<CuisineModelResponse> toCollectionModel(Iterable<? extends Cuisine> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CityController.class).withSelfRel());
	}
	
	@Override
	public void copyToEntity(CuisineModelRequest request, Cuisine entity) {
		modelMapper.map(request, entity);
	}
}
