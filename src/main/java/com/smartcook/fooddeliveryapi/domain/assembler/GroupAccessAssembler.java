package com.smartcook.fooddeliveryapi.domain.assembler;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.GroupAccessController;
import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;
import com.smartcook.fooddeliveryapi.domain.model.request.GroupAccessModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.GroupAccessModelResponse;

@Component
public class GroupAccessAssembler extends AbstractAssembler<GroupAccess, GroupAccessModelRequest, GroupAccessModelResponse> {

	public GroupAccessAssembler() {
		super(GroupAccessController.class, GroupAccessModelResponse.class);
	}

	@Override
	public GroupAccess toEntity(GroupAccessModelRequest request) {
		return modelMapper.map(request, GroupAccess.class);
	}

	@Override
	public GroupAccessModelResponse toModel(GroupAccess entity) {
		return modelMapper.map(entity, GroupAccessModelResponse.class);
	}

	@Override
	public void copyToEntity(GroupAccessModelRequest request, GroupAccess entity) {
		modelMapper.map(request, entity);
	}

}
