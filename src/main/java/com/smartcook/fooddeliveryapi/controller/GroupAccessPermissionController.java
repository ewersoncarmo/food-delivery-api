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

import com.smartcook.fooddeliveryapi.domain.assembler.PermissionAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.PermissionModelResponse;
import com.smartcook.fooddeliveryapi.service.GroupAccessService;

@RestController
@RequestMapping("/api/v1/groups-access/{groupAccessId}/permissions")
public class GroupAccessPermissionController {

	@Autowired
	private GroupAccessService groupAccessService;
	
	@Autowired
	private PermissionAssembler permissionAssembler;
	
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<PermissionModelResponse>>> findPermissions(@PathVariable("groupAccessId") Long groupAccessId) {
		GroupAccess groupAccess = groupAccessService.findById(groupAccessId);

		CollectionModel<PermissionModelResponse> permissions = permissionAssembler.toCollectionModel(groupAccess.getPermissions().
																											stream().collect(Collectors.toList()));
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(permissions));
	}
	
	@PutMapping("/{permissionId}")
	public ResponseEntity<Void> addPermission(@PathVariable("groupAccessId") Long groupAccessId,
			@PathVariable("permissionId") Long permissionId) {
		groupAccessService.addPermission(groupAccessId, permissionId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@DeleteMapping("/{permissionId}")
	public ResponseEntity<Void> removePermission(@PathVariable("groupAccessId") Long groupAccessId,
			@PathVariable("permissionId") Long permissionId) {
		groupAccessService.removePermission(groupAccessId, permissionId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
}
