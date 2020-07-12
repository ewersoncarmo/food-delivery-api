package com.smartcook.fooddeliveryapi.domain.assembler;

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
		return modelMapper.map(entity, PermissionModelResponse.class);
	}
	
	@Override
	public void copyToEntity(PermissionModelRequest request, Permission entity) {
		modelMapper.map(request, entity);
	}
}
