package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
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
		// add self relation		
		StateModelResponse stateModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, stateModelResponse);
		
		// add collection relation
		stateModelResponse.add(linkTo(StateController.class).withRel("states"));
		
		return stateModelResponse;
	}
	
	@Override
	public CollectionModel<StateModelResponse> toCollectionModel(Iterable<? extends State> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(StateController.class).withSelfRel());
	}
	
	@Override
	public void copyToEntity(StateModelRequest request, State entity) {
		modelMapper.map(request, entity);
	}
}
