package com.smartcook.fooddeliveryapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
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
	public ResponseEntity<ModelResponse<CollectionModel<UserModelResponse>>> findResponsibleUsers(@PathVariable("restaurantId") Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		CollectionModel<UserModelResponse> responsibleUsers = userAssembler.toCollectionModel(restaurant.getResponsibleUsers().
																								stream().collect(Collectors.toList()))
				.removeLinks()
				.add(linkTo(methodOn(RestaurantResponsibleUserController.class).findResponsibleUsers(restaurantId)).withSelfRel());
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
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
