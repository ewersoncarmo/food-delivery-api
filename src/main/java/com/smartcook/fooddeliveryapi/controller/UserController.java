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
import com.smartcook.fooddeliveryapi.domain.assembler.UserAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.domain.model.request.UserChangePasswordModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.request.UserModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.request.UserPasswordModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.UserModelResponse;
import com.smartcook.fooddeliveryapi.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAssembler userAssembler;
	
	// This method is public. It musn't require authentication
	@PostMapping
	public ResponseEntity<ModelResponse<UserModelResponse>> create(@Valid @RequestBody UserPasswordModelRequest userPasswordModelRequest) {
		User user = userAssembler.toEntity(userPasswordModelRequest);
		
		userService.create(user);
		
		UserModelResponse userModelResponse = userAssembler.toModel(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(userModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(userModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<UserModelResponse>>> findAll() {
		List<User> users = userService.findAll();

		CollectionModel<UserModelResponse> userModelResponse = userAssembler.toCollectionModel(users);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(userModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<UserModelResponse>> findById(@PathVariable("id") Long id) {
		User user = userService.findById(id);

		UserModelResponse userModelResponse = userAssembler.toModel(user);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(userModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEditData
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<UserModelResponse>> update(@Valid @RequestBody UserModelRequest userModelRequest,
			@PathVariable("id") Long id) {
		User user = userService.findById(id);
		
		userAssembler.copyToEntity(userModelRequest, user);
		
		userService.update(user);
		
		UserModelResponse userModelResponse = userAssembler.toModel(user);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(userModelResponse));
	}
	
	@CheckSecurity.UsersGroupsPermissions.CanEditPassword
	@PutMapping("/{id}/change-password")
	public ResponseEntity<Void> changePassword(@Valid @RequestBody UserChangePasswordModelRequest userChangePasswordModelRequest,
			@PathVariable("id") Long id) {
		userService.changePassword(id, userChangePasswordModelRequest.getCurrentPassword(), userChangePasswordModelRequest.getNewPassword());
		
		return ResponseEntity.noContent()
				.build();
	}

	@CheckSecurity.UsersGroupsPermissions.CanEditData
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		userService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
