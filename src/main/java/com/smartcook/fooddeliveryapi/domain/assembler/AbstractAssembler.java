package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAssembler<Entity, Request, Response> {

	@Autowired
	protected ModelMapper modelMapper;
	
	public abstract Entity toEntity(Request request);
	
	public abstract Response toModel(Entity entity);
	
	public abstract List<Response> toCollectionModel(List<Entity> entityList);
	
	public abstract void copyToEntity(Request request, Entity entity);
	
}
