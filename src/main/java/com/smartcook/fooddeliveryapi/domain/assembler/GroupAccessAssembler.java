package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;
import com.smartcook.fooddeliveryapi.domain.model.request.GroupAccessModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.GroupAccessModelResponse;

@Component
public class GroupAccessAssembler extends AbstractAssembler<GroupAccess, GroupAccessModelRequest, GroupAccessModelResponse> {

	@Override
	public GroupAccess toEntity(GroupAccessModelRequest request) {
		return modelMapper.map(request, GroupAccess.class);
	}

	@Override
	public GroupAccessModelResponse toModel(GroupAccess entity) {
		return modelMapper.map(entity, GroupAccessModelResponse.class);
	}

	@Override
	public List<GroupAccessModelResponse> toCollectionModel(List<GroupAccess> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}

	@Override
	public void copyToEntity(GroupAccessModelRequest request, GroupAccess entity) {
		modelMapper.map(request, entity);
	}

}
