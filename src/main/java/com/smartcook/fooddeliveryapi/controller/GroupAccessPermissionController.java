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

import com.smartcook.fooddeliveryapi.controller.security.CheckSecurity;
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
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<PermissionModelResponse>>> findPermissions(@PathVariable("groupAccessId") Long groupAccessId) {
		GroupAccess groupAccess = groupAccessService.findById(groupAccessId);

		CollectionModel<PermissionModelResponse> permissions = permissionAssembler.toCollectionModel(groupAccess.getPermissions().
																											stream().collect(Collectors.toList()));
		
		permissions.removeLinks()
			.add(linkTo(methodOn(GroupAccessPermissionController.class).findPermissions(groupAccessId)).withSelfRel())
			.add(linkTo(methodOn(GroupAccessPermissionController.class).addPermission(groupAccessId, null)).withRel("add"));
		
		permissions.getContent().forEach(permission -> {
			permission.add(linkTo(methodOn(GroupAccessPermissionController.class).removePermission(groupAccessId, permission.getId())).withRel("remove"));
		});
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(permissions));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@PutMapping("/{permissionId}")
	public ResponseEntity<Void> addPermission(@PathVariable("groupAccessId") Long groupAccessId,
			@PathVariable("permissionId") Long permissionId) {
		groupAccessService.addPermission(groupAccessId, permissionId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@DeleteMapping("/{permissionId}")
	public ResponseEntity<Void> removePermission(@PathVariable("groupAccessId") Long groupAccessId,
			@PathVariable("permissionId") Long permissionId) {
		groupAccessService.removePermission(groupAccessId, permissionId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
}
