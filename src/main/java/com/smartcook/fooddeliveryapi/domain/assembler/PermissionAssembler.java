package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.PermissionController;
import com.smartcook.fooddeliveryapi.domain.entity.Permission;
import com.smartcook.fooddeliveryapi.domain.model.request.PermissionModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.PermissionModelResponse;

@Component
public class PermissionAssembler extends AbstractAssembler<Permission, PermissionModelRequest, PermissionModelResponse> {

	public PermissionAssembler() {
		super(PermissionController.class, PermissionModelResponse.class);
	}

	@Override
	public Permission toEntity(PermissionModelRequest request) {
		return modelMapper.map(request, Permission.class);
	}
	
	@Override
	public PermissionModelResponse toModel(Permission entity) {
		PermissionModelResponse permissionModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, permissionModelResponse);
		
		permissionModelResponse.add(linkTo(PermissionController.class).withRel("permissions"));
		
		return permissionModelResponse;
	}
	
	@Override
	public CollectionModel<PermissionModelResponse> toCollectionModel(Iterable<? extends Permission> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(PermissionController.class).withSelfRel());
	}
	
	@Override
	public void copyToEntity(PermissionModelRequest request, Permission entity) {
		modelMapper.map(request, entity);
	}
}
