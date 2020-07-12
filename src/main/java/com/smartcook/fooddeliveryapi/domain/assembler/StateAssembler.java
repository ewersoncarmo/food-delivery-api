package com.smartcook.fooddeliveryapi.domain.assembler;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.StateController;
import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.domain.model.request.StateModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.StateModelResponse;

@Component
public class StateAssembler extends AbstractAssembler<State, StateModelRequest, StateModelResponse> {

	public StateAssembler() {
		super(StateController.class, StateModelResponse.class);
	}

	@Override
	public State toEntity(StateModelRequest request) {
		return modelMapper.map(request, State.class);
	}
	
	@Override
	public StateModelResponse toModel(State entity) {
		return modelMapper.map(entity, StateModelResponse.class);
	}
	
	@Override
	public void copyToEntity(StateModelRequest request, State entity) {
		modelMapper.map(request, entity);
	}
}
