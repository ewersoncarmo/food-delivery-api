package com.smartcook.fooddeliveryapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcook.fooddeliveryapi.domain.assembler.UserAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.UserModelResponse;
import com.smartcook.fooddeliveryapi.service.RestaurantService;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/responsible-users")
public class RestaurantResponsibleUserController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserAssembler userAssembler;
	
	@GetMapping
	public ResponseEntity<ModelResponse<List<UserModelResponse>>> findResponsibleUsers(@PathVariable("restaurantId") Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		List<UserModelResponse> responsibleUsers = userAssembler.toCollectionModel(restaurant.getResponsibleUsers().
																								stream().collect(Collectors.toList()));
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(responsibleUsers));
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Void> addResponsibleUser(@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("userId") Long userId) {
		restaurantService.addResponsibleUser(restaurantId, userId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> removeResponsibleUser(@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("userId") Long userId) {
		restaurantService.removeResponsibleUser(restaurantId, userId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
}