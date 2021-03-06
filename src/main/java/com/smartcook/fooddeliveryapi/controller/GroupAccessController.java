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
import com.smartcook.fooddeliveryapi.domain.assembler.GroupAccessAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;
import com.smartcook.fooddeliveryapi.domain.model.request.GroupAccessModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.GroupAccessModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.service.GroupAccessService;

@RestController
@RequestMapping("/api/v1/groups-access")
public class GroupAccessController {

	@Autowired
	private GroupAccessService groupAccessService;
	
	@Autowired
	private GroupAccessAssembler groupAccessAssembler;
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@PostMapping
	public ResponseEntity<ModelResponse<GroupAccessModelResponse>> create(@Valid @RequestBody GroupAccessModelRequest groupAccessModelRequest) {
		GroupAccess groupAccess = groupAccessAssembler.toEntity(groupAccessModelRequest);
		
		groupAccessService.create(groupAccess);
		
		GroupAccessModelResponse groupAccessModelResponse = groupAccessAssembler.toModel(groupAccess);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(groupAccessModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(groupAccessModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<GroupAccessModelResponse>>> findAll() {
		List<GroupAccess> groupsAccess = groupAccessService.findAll();

		CollectionModel<GroupAccessModelResponse> groupAccessModelResponse = groupAccessAssembler.toCollectionModel(groupsAccess);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(groupAccessModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<GroupAccessModelResponse>> findById(@PathVariable("id") Long id) {
		GroupAccess groupAccess = groupAccessService.findById(id);

		GroupAccessModelResponse groupAccessModelResponse = groupAccessAssembler.toModel(groupAccess);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(groupAccessModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<GroupAccessModelResponse>> update(@Valid @RequestBody GroupAccessModelRequest groupAccessModelRequest,
			@PathVariable("id") Long id) {
		GroupAccess groupAccess = groupAccessService.findById(id);
		
		groupAccessAssembler.copyToEntity(groupAccessModelRequest, groupAccess);
		
		groupAccessService.update(groupAccess);
		
		GroupAccessModelResponse groupAccessModelResponse = groupAccessAssembler.toModel(groupAccess);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(groupAccessModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		groupAccessService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
