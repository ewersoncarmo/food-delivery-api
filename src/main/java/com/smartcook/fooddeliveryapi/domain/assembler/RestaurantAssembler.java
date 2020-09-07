package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.CityController;
import com.smartcook.fooddeliveryapi.controller.CuisineController;
import com.smartcook.fooddeliveryapi.controller.RestaurantController;
import com.smartcook.fooddeliveryapi.controller.StateController;
import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.request.RestaurantModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.RestaurantModelResponse;

@Component
public class RestaurantAssembler extends AbstractAssembler<Restaurant, RestaurantModelRequest, RestaurantModelResponse>{

	public RestaurantAssembler() {
		super(RestaurantController.class, RestaurantModelResponse.class);
	}

	@Override
	public Restaurant toEntity(RestaurantModelRequest request) {
		return modelMapper.map(request, Restaurant.class);
	}
	
	@Override
	public RestaurantModelResponse toModel(Restaurant entity) {
		// add self relation
		RestaurantModelResponse restaurantModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, restaurantModelResponse);
		
		TemplateVariables pageVariables = new TemplateVariables(
				new TemplateVariable("page", VariableType.REQUEST_PARAM),
				new TemplateVariable("size", VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", VariableType.REQUEST_PARAM));
		
		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("cuisineId", VariableType.REQUEST_PARAM),
				new TemplateVariable("cityId", VariableType.REQUEST_PARAM),
				new TemplateVariable("active", VariableType.REQUEST_PARAM),
				new TemplateVariable("open", VariableType.REQUEST_PARAM));
		
		// add self relation to cuisine
		restaurantModelResponse.getCuisine().add(linkTo(methodOn(CuisineController.class).findById(restaurantModelResponse.getCuisine().getId())).withSelfRel());
		// add self relation to city
		restaurantModelResponse.getAddress().getCity().add(linkTo(methodOn(CityController.class).findById(restaurantModelResponse.getAddress().getCity().getId())).withSelfRel());
		// add self relation to state
		restaurantModelResponse.getAddress().getCity().getState().add(linkTo(methodOn(StateController.class).findById(restaurantModelResponse.getAddress().getCity().getState().getId())).withSelfRel());
		
		String restaurantUrl = linkTo(RestaurantController.class).toUri().toString();
		// add collection relation
		restaurantModelResponse.add(new Link(UriTemplate.of(restaurantUrl, pageVariables.concat(filterVariables)), "restaurants"));
		
		return restaurantModelResponse;
	}
	
	@Override
	public void copyToEntity(RestaurantModelRequest request, Restaurant entity) {
		entity.setCuisine(new Cuisine());
		entity.getAddress().setCity(new City());
		
		modelMapper.map(request, entity);
	}

}
