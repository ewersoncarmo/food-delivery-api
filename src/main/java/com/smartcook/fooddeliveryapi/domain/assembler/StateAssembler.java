package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.domain.model.request.StateModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.StateModelResponse;

@Component
public class StateAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public State toEntity(StateModelRequest stateModelRequest) {
		return modelMapper.map(stateModelRequest, State.class);
	}
	
	public StateModelResponse toModel(State state) {
		return modelMapper.map(state, StateModelResponse.class);
	}
	
	public List<StateModelResponse> toCollectionModel(List<State> states) {
		return states.stream()
				.map(state -> toModel(state))
				.collect(Collectors.toList());
	}
	
	public void copyToEntity(StateModelRequest stateModelRequest, State state) {
		modelMapper.map(stateModelRequest, state);
	}
}
