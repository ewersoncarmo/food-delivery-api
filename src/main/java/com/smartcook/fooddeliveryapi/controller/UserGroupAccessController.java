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
import com.smartcook.fooddeliveryapi.domain.assembler.GroupAccessAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.domain.model.response.GroupAccessModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.service.UserService;

@RestController
@RequestMapping("/api/v1/users/{userId}/groups-access")
public class UserGroupAccessController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupAccessAssembler groupAccessAssembler;
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<GroupAccessModelResponse>>> findGroupsAccess(@PathVariable("userId") Long userId) {
		User user = userService.findById(userId);

		CollectionModel<GroupAccessModelResponse> groupsAccess = groupAccessAssembler.toCollectionModel(user.getGroups().
																									stream().collect(Collectors.toList()));
		
		groupsAccess.removeLinks()
			.add(linkTo(methodOn(UserGroupAccessController.class).findGroupsAccess(userId)).withSelfRel())
		    .add(linkTo(methodOn(UserGroupAccessController.class).addGroupAccess(userId, null)).withRel("add"));

		groupsAccess.getContent().forEach(groupAccess -> {
			groupAccess.add(linkTo(methodOn(UserGroupAccessController.class).removeGroupAccess(userId, groupAccess.getId())).withRel("remove"));
		});
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(groupsAccess));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@PutMapping("/{groupAccessId}")
	public ResponseEntity<Void> addGroupAccess(@PathVariable("userId") Long userId,
			@PathVariable("groupAccessId") Long groupAccessId) {
		userService.addGroupAccess(userId, groupAccessId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEdit
	@DeleteMapping("/{groupAccessId}")
	public ResponseEntity<Void> removeGroupAccess(@PathVariable("userId") Long userId,
			@PathVariable("groupAccessId") Long groupAccessId) {
		userService.removeGroupAccess(userId, groupAccessId);
		
		return ResponseEntity.noContent()
				.build();
	}
	
}
