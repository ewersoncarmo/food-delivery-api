package com.smartcook.fooddeliveryapi.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrderItem;
import com.smartcook.fooddeliveryapi.domain.model.request.PurchaseOrderItemModelRequest;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(PurchaseOrderItemModelRequest.class, PurchaseOrderItem.class)
		.addMappings(mapper -> mapper.skip(PurchaseOrderItem::setId));
		
		return modelMapper;
	}
}
