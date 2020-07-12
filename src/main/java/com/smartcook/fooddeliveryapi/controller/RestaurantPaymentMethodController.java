package com.smartcook.fooddeliveryapi.controller;

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

import com.smartcook.fooddeliveryapi.domain.assembler.PaymentMethodAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.PaymentMethodModelResponse;
import com.smartcook.fooddeliveryapi.service.RestaurantService;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/payment-methods")
public class RestaurantPaymentMethodController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private PaymentMethodAssembler paymentMethodAssembler;
	
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<PaymentMethodModelResponse>>> findPaymentMethods(@PathVariable("restaurantId") Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		CollectionModel<PaymentMethodModelResponse> paymentMethods = paymentMethodAssembler.toCollectionModel(restaurant.getPaymentMethods().
																												stream().collect(Collectors.toList()));
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(paymentMethods));
	}
	
	@PutMapping("/{paymentMethodId}")
	public ResponseEntity<Void> addPaymentMethod(@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("paymentMethodId") Long paymentMethodId) {
		restaurantService.addPaymentMethod(restaurantId, paymentMethodId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@DeleteMapping("/{paymentMethodId}")
	public ResponseEntity<Void> removePaymentMethod(@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("paymentMethodId") Long paymentMethodId) {
		restaurantService.removePaymentMethod(restaurantId, paymentMethodId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
}
