package com.smartcook.fooddeliveryapi.domain.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.ProductPhoto;
import com.smartcook.fooddeliveryapi.domain.model.response.ProductPhotoModelResponse;

@Component
public class ProductPhotoAssembler {

	@Autowired
	protected ModelMapper modelMapper;
	
	public ProductPhotoModelResponse toModel(ProductPhoto entity) {
		return modelMapper.map(entity, ProductPhotoModelResponse.class);
	}

}
