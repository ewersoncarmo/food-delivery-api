package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.domain.model.request.UserModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.UserModelResponse;

@Component
public class UserAssembler extends AbstractAssembler<User, UserModelRequest, UserModelResponse> {

	@Override
	public User toEntity(UserModelRequest request) {
		return modelMapper.map(request, User.class);
	}

	@Override
	public UserModelResponse toModel(User entity) {
		return modelMapper.map(entity, UserModelResponse.class);
	}

	@Override
	public List<UserModelResponse> toCollectionModel(List<User> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}

	@Override
	public void copyToEntity(UserModelRequest request, User entity) {
		modelMapper.map(request, entity);
	}

}
