package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
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
import com.smartcook.fooddeliveryapi.domain.assembler.CuisineAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.model.request.CuisineModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CuisineModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.service.CuisineService;

@RestController
@RequestMapping("/api/v1/cuisines")
public class CuisineController {

	@Autowired
	private CuisineService cuisineService;
	
	@Autowired
	private CuisineAssembler cuisineAssembler;
	
	@CheckSecurity.Cuisines.CanEdit
	@PostMapping
	public ResponseEntity<ModelResponse<CuisineModelResponse>> create(@Valid @RequestBody CuisineModelRequest cuisineModelRequest) {
		Cuisine cuisine = cuisineAssembler.toEntity(cuisineModelRequest);
		
		cuisineService.create(cuisine);
		
		CuisineModelResponse cuisineModelResponse = cuisineAssembler.toModel(cuisine);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cuisineModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(cuisineModelResponse));
	}
	
	@CheckSecurity.Cuisines.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<CuisineModelResponse>>> findAll() {
		List<Cuisine> cuisines = cuisineService.findAll();

		CollectionModel<CuisineModelResponse> cuisineModelResponse = cuisineAssembler.toCollectionModel(cuisines);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(cuisineModelResponse));
	}
	
	@CheckSecurity.Cuisines.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<CuisineModelResponse>> findById(@PathVariable("id") Long id) {
		Cuisine cuisine = cuisineService.findById(id);

		CuisineModelResponse cuisineModelResponse = cuisineAssembler.toModel(cuisine);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(cuisineModelResponse));
	}
	
	@CheckSecurity.Cuisines.CanEdit
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<CuisineModelResponse>> update(@Valid @RequestBody CuisineModelRequest cuisineModelRequest,
			@PathVariable("id") Long id) {
		Cuisine cuisine = cuisineService.findById(id);
		
		cuisineAssembler.copyToEntity(cuisineModelRequest, cuisine);
		
		cuisineService.update(cuisine);
		
		CuisineModelResponse cuisineModelResponse = cuisineAssembler.toModel(cuisine);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(cuisineModelResponse));
	}

	@CheckSecurity.Cuisines.CanEdit
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		cuisineService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
