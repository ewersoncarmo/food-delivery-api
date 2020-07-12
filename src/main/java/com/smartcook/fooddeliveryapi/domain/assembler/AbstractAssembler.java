package com.smartcook.fooddeliveryapi.domain.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public abstract class AbstractAssembler<Entity, Request, Response extends RepresentationModel<?>> 
	extends RepresentationModelAssemblerSupport<Entity, Response>  {

	@Autowired
	protected ModelMapper modelMapper;

	public AbstractAssembler(Class<?> controllerClass, Class<Response> resourceType) {
		super(controllerClass, resourceType);
	}
	
	public abstract Entity toEntity(Request request);
	
	public abstract void copyToEntity(Request request, Entity entity);
	
}
