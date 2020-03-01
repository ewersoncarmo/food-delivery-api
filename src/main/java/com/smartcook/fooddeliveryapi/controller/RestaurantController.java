package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.smartcook.fooddeliveryapi.domain.assembler.RestaurantAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
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
	
	@GetMapping
	public ResponseEntity<ModelResponse<List<RestaurantModelResponse>>> findAll() {
		List<Restaurant> restaurant = restaurantService.findAll();

		List<RestaurantModelResponse> restaurantModelResponse = restaurantAssembler.toCollectionModel(restaurant);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(restaurantModelResponse));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<RestaurantModelResponse>> findById(@PathVariable("id") Long id) {
		Restaurant restaurant = restaurantService.findById(id);

		RestaurantModelResponse restaurantModelResponse = restaurantAssembler.toModel(restaurant);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(restaurantModelResponse));
	}
	
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		restaurantService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
