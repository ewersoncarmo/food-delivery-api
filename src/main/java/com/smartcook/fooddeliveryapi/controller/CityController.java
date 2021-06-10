package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import com.smartcook.fooddeliveryapi.configuration.security.AuthenticationSecurityConfig;
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
import com.smartcook.fooddeliveryapi.domain.assembler.CityAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.model.request.CityModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.CityModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.service.CityService;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

	@Autowired
	private CityService cityService;
	
	@Autowired
	private CityAssembler cityAssembler;

	@Autowired
	private AuthenticationSecurityConfig authenticationSecurityConfig;
	
	@CheckSecurity.Cities.CanEdit
	@PostMapping
	public ResponseEntity<ModelResponse<CityModelResponse>> create(@Valid @RequestBody CityModelRequest cityModelRequest) {
		City city = cityAssembler.toEntity(cityModelRequest);
		
		cityService.create(city);
		
		CityModelResponse cityModelResponse = cityAssembler.toModel(city);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cityModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(cityModelResponse));
	}
	
//	@CheckSecurity.Cities.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<CityModelResponse>>> findAll() {
		authenticationSecurityConfig.getAuthenticationTest();
		List<City> cities = cityService.findAll();

		CollectionModel<CityModelResponse> cityModelResponse = cityAssembler.toCollectionModel(cities);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(cityModelResponse));
	}
	
	@CheckSecurity.Cities.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<CityModelResponse>> findById(@PathVariable("id") Long id) {
		City city = cityService.findById(id);

		CityModelResponse cityModelResponse = cityAssembler.toModel(city);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(cityModelResponse));
	}
	
	@CheckSecurity.Cities.CanEdit
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<CityModelResponse>> update(@Valid @RequestBody CityModelRequest cityModelRequest,
			@PathVariable("id") Long id) {
		City city = cityService.findById(id);
		
		cityAssembler.copyToEntity(cityModelRequest, city);
		
		cityService.update(city);
		
		CityModelResponse cityModelResponse = cityAssembler.toModel(city);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(cityModelResponse));
	}
	
	@CheckSecurity.Cities.CanEdit
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		cityService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
