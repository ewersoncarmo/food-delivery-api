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
import com.smartcook.fooddeliveryapi.domain.assembler.PermissionAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.Permission;
import com.smartcook.fooddeliveryapi.domain.model.request.PermissionModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.PermissionModelResponse;
import com.smartcook.fooddeliveryapi.service.PermissionService;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private PermissionAssembler permissionAssembler;
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@PostMapping
	public ResponseEntity<ModelResponse<PermissionModelResponse>> create(@Valid @RequestBody PermissionModelRequest permissionModelRequest) {
		Permission permission = permissionAssembler.toEntity(permissionModelRequest);
		
		permissionService.create(permission);
		
		PermissionModelResponse permissionModelResponse = permissionAssembler.toModel(permission);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(permissionModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(permissionModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<PermissionModelResponse>>> findAll() {
		List<Permission> permissions = permissionService.findAll();

		CollectionModel<PermissionModelResponse> permissionsModelResponse = permissionAssembler.toCollectionModel(permissions);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(permissionsModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<PermissionModelResponse>> findById(@PathVariable("id") Long id) {
		Permission permission = permissionService.findById(id);

		PermissionModelResponse permissionModelResponse = permissionAssembler.toModel(permission);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(permissionModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<PermissionModelResponse>> update(@Valid @RequestBody PermissionModelRequest permissionModelRequest,
			@PathVariable("id") Long id) {
		Permission permission = permissionService.findById(id);
		
		permissionAssembler.copyToEntity(permissionModelRequest, permission);
		
		permissionService.update(permission);
		
		PermissionModelResponse permissionModelResponse = permissionAssembler.toModel(permission);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(permissionModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		permissionService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
