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

import com.smartcook.fooddeliveryapi.domain.assembler.CityAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.model.request.CityModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.CityModelResponse;
import com.smartcook.fooddeliveryapi.service.CityService;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

	@Autowired
	private CityService cityService;
	
	@Autowired
	private CityAssembler cityAssembler;
	
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
	
	@GetMapping
	public ResponseEntity<ModelResponse<List<CityModelResponse>>> findAll() {
		List<City> cities = cityService.findAll();

		List<CityModelResponse> cityModelResponse = cityAssembler.toCollectionModel(cities);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(cityModelResponse));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<CityModelResponse>> findById(@PathVariable("id") Long id) {
		City city = cityService.findById(id);

		CityModelResponse cityModelResponse = cityAssembler.toModel(city);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(cityModelResponse));
	}
	
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		cityService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
