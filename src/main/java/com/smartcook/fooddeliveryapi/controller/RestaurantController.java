package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartcook.fooddeliveryapi.controller.security.CheckSecurity;
import com.smartcook.fooddeliveryapi.domain.assembler.RestaurantAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.filter.RestaurantFilter;
import com.smartcook.fooddeliveryapi.domain.model.request.RestaurantIdModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.request.RestaurantModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.RestaurantModelResponse;
import com.smartcook.fooddeliveryapi.service.RestaurantService;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private RestaurantAssembler restaurantAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Restaurant> pagedResourceRestaurantAssembler;
	
	@CheckSecurity.Restaurants.CanEdit
	@PostMapping
	public ResponseEntity<ModelResponse<RestaurantModelResponse>> create(@Valid @RequestBody RestaurantModelRequest restaurantModelRequest) {
		Restaurant restaurant = restaurantAssembler.toEntity(restaurantModelRequest);
		
		restaurantService.create(restaurant);
		
		RestaurantModelResponse restaurantModelResponse = restaurantAssembler.toModel(restaurant);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(restaurantModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(restaurantModelResponse));
	}
	
	// TODO - create a new Response specific for this method, which will return only some properties
	// TODO - create a new Assembler to handle this new Response
	@CheckSecurity.Restaurants.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<PagedModel<RestaurantModelResponse>>> search(RestaurantFilter filter, 
			Pageable pageable) {
		Page<Restaurant> restaurantPage = restaurantService.search(filter, pageable);

		PagedModel<RestaurantModelResponse> pagedModel = pagedResourceRestaurantAssembler.toModel(restaurantPage, restaurantAssembler);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(pagedModel));
	}
	
	@CheckSecurity.Restaurants.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<RestaurantModelResponse>> findById(@PathVariable("id") Long id) {
		Restaurant restaurant = restaurantService.findById(id);

		RestaurantModelResponse restaurantModelResponse = restaurantAssembler.toModel(restaurant);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(restaurantModelResponse));
	}
	
	@CheckSecurity.Restaurants.CanEdit
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<RestaurantModelResponse>> update(@Valid @RequestBody RestaurantModelRequest restaurantModelRequest,
			@PathVariable("id") Long id) {
		Restaurant restaurant = restaurantService.findById(id);
		
		restaurantAssembler.copyToEntity(restaurantModelRequest, restaurant);
		
		restaurantService.update(restaurant);
		
		RestaurantModelResponse restaurantModelResponse = restaurantAssembler.toModel(restaurant);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(restaurantModelResponse));
	}
	
	@CheckSecurity.Restaurants.CanEdit
	@PostMapping("/{id}/activate")
	public ResponseEntity<Void> activate(@PathVariable("id") Long id) {
		restaurantService.activate(id);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.Restaurants.CanEdit
	@PostMapping("/{id}/deactivate")
	public ResponseEntity<Void> deactivate(@PathVariable("id") Long id) {
		restaurantService.deactivate(id);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.Restaurants.CanEdit
	@PostMapping("/activate")
	public ResponseEntity<Void> activateBatch(@Valid @RequestBody List<RestaurantIdModelRequest> restaurantIdModelRequest) {
		restaurantService.activate(restaurantIdModelRequest.stream().map(r -> {
			return r.getId();
		}).collect(Collectors.toList()));
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.Restaurants.CanEdit
	@PostMapping("/deactivate")
	public ResponseEntity<Void> deactivateBatch(@Valid @RequestBody List<RestaurantIdModelRequest> restaurantIdModelRequest) {
		restaurantService.deactivate(restaurantIdModelRequest.stream().map(r -> {
			return r.getId();
		}).collect(Collectors.toList()));
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.Restaurants.CanManage
	@PostMapping("/{id}/opening")
	public ResponseEntity<Void> opening(@PathVariable("id") Long id) {
		restaurantService.opening(id);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.Restaurants.CanManage
	@PostMapping("/{id}/closing")
	public ResponseEntity<Void> closing(@PathVariable("id") Long id) {
		restaurantService.closing(id);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.Restaurants.CanEdit
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		restaurantService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
