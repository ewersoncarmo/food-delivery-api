package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.domain.model.request.StateModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.StateModelResponse;

@Component
public class StateAssembler extends AbstractAssembler<State, StateModelRequest, StateModelResponse> {

	@Override
	public State toEntity(StateModelRequest request) {
		return modelMapper.map(request, State.class);
	}
	
	@Override
	public StateModelResponse toModel(State entity) {
		return modelMapper.map(entity, StateModelResponse.class);
	}
	
	@Override
	public List<StateModelResponse> toCollectionModel(List<State> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}
	
	@Override
	public void copyToEntity(StateModelRequest request, State entity) {
		modelMapper.map(request, entity);
	}
}
