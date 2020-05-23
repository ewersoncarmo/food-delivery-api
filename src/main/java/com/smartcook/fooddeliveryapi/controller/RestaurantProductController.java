package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartcook.fooddeliveryapi.domain.assembler.ProductAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.Product;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.request.ProductModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ProductModelResponse;
import com.smartcook.fooddeliveryapi.service.ProductService;
import com.smartcook.fooddeliveryapi.service.RestaurantService;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/products")
public class RestaurantProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductAssembler productAssembler;

	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping
	public ResponseEntity<ModelResponse<ProductModelResponse>> create(@PathVariable("restaurantId") Long restaurantId,
			@Valid @RequestBody ProductModelRequest productModelRequest) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		Product product = productAssembler.toEntity(productModelRequest);
		product.setRestaurant(restaurant);
		
		productService.create(product);
		
		ProductModelResponse productModelResponse = productAssembler.toModel(product);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(productModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(productModelResponse));
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ModelResponse<ProductModelResponse>> update(@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("productId") Long productId, @Valid @RequestBody ProductModelRequest productModelRequest) {
		Product product = productService.findByRestaurant(restaurantId, productId);

		productAssembler.copyToEntity(productModelRequest, product);
		
		productService.update(product);
		
		ProductModelResponse productModelResponse = productAssembler.toModel(product);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(productModelResponse));
	}
	
	@GetMapping
	public ResponseEntity<ModelResponse<List<ProductModelResponse>>> findAll(@PathVariable("restaurantId") Long restaurantId,
			@RequestParam(value = "includeInactive", required = false) boolean includeInactive) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		List<ProductModelResponse> products = null;
		
		if (!includeInactive) {
			products = productAssembler.toCollectionModel(restaurant.getProducts().stream().filter(p -> p.getActive() == true).collect(Collectors.toList()));
		} else {
			products = productAssembler.toCollectionModel(restaurant.getProducts().stream().collect(Collectors.toList()));
		}
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(products));
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ModelResponse<ProductModelResponse>> findById(@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("productId") Long productId) {
		Product product = productService.findByRestaurant(restaurantId, productId);

		ProductModelResponse productModelResponse = productAssembler.toModel(product);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(productModelResponse));
	}

}
