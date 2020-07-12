package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.UserController;
import com.smartcook.fooddeliveryapi.controller.UserGroupAccessController;
import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.domain.model.request.UserModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.UserModelResponse;

@Component
public class UserAssembler extends AbstractAssembler<User, UserModelRequest, UserModelResponse> {

	public UserAssembler() {
		super(UserController.class, UserModelResponse.class);
	}

	@Override
	public User toEntity(UserModelRequest request) {
		return modelMapper.map(request, User.class);
	}

	@Override
	public UserModelResponse toModel(User entity) {
		// add self relation		
		UserModelResponse userModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, userModelResponse);
		
		// add collection relation
		userModelResponse.add(linkTo(UserController.class).withRel("users"));
		// add self relation to group access
		userModelResponse.add(linkTo(methodOn(UserGroupAccessController.class).findGroupsAccess(userModelResponse.getId())).withRel("groups-access"));
		
		return userModelResponse;
	}

	@Override
	public CollectionModel<UserModelResponse> toCollectionModel(Iterable<? extends User> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(UserController.class).withSelfRel());
	}
	
	@Override
	public void copyToEntity(UserModelRequest request, User entity) {
		modelMapper.map(request, entity);
	}

}
