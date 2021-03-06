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
import com.smartcook.fooddeliveryapi.domain.assembler.StateAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.domain.model.request.StateModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.StateModelResponse;
import com.smartcook.fooddeliveryapi.service.StateService;

@RestController
@RequestMapping("/api/v1/states")
public class StateController {

	@Autowired
	private StateService stateService;
	
	@Autowired
	private StateAssembler stateAssembler;
	
	@CheckSecurity.States.CanEdit
	@PostMapping
	public ResponseEntity<ModelResponse<StateModelResponse>> create(@Valid @RequestBody StateModelRequest stateModelRequest) {
		State state = stateAssembler.toEntity(stateModelRequest);
		
		stateService.create(state);
		
		StateModelResponse stateModelResponse = stateAssembler.toModel(state);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(stateModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(stateModelResponse));
	}
	
	@CheckSecurity.States.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<StateModelResponse>>> findAll() {
		List<State> states = stateService.findAll();

		CollectionModel<StateModelResponse> stateModelResponse = stateAssembler.toCollectionModel(states);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(stateModelResponse));
	}
	
	@CheckSecurity.States.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<StateModelResponse>> findById(@PathVariable("id") Long id) {
		State state = stateService.findById(id);

		StateModelResponse stateModelResponse = stateAssembler.toModel(state);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(stateModelResponse));
	}
	
	@CheckSecurity.States.CanEdit
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<StateModelResponse>> update(@Valid @RequestBody StateModelRequest stateModelRequest,
			@PathVariable("id") Long id) {
		State state = stateService.findById(id);
		
		stateAssembler.copyToEntity(stateModelRequest, state);
		
		stateService.update(state);
		
		StateModelResponse stateModelResponse = stateAssembler.toModel(state);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(stateModelResponse));
	}
	
	@CheckSecurity.States.CanEdit
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		stateService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
