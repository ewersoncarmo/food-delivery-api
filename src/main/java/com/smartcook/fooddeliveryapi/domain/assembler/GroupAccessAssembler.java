package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.GroupAccessController;
import com.smartcook.fooddeliveryapi.controller.GroupAccessPermissionController;
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
		// add self relation
		GroupAccessModelResponse groupAccessModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, groupAccessModelResponse);
		
		// add collection relation
		groupAccessModelResponse.add(linkTo(GroupAccessController.class).withRel("groups-access"));
		// add relation to permissions
		groupAccessModelResponse.add(linkTo(methodOn(GroupAccessPermissionController.class).findPermissions(groupAccessModelResponse.getId())).withRel("permissions"));

		return groupAccessModelResponse;
	}
	
	@Override
	public CollectionModel<GroupAccessModelResponse> toCollectionModel(Iterable<? extends GroupAccess> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(GroupAccessController.class).withSelfRel());
	}

	@Override
	public void copyToEntity(GroupAccessModelRequest request, GroupAccess entity) {
		modelMapper.map(request, entity);
	}

}
