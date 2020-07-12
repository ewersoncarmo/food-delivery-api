package com.smartcook.fooddeliveryapi.domain.assembler;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.controller.RestaurantProductController;
import com.smartcook.fooddeliveryapi.domain.entity.Product;
import com.smartcook.fooddeliveryapi.domain.model.request.ProductModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ProductModelResponse;

@Component
public class ProductAssembler extends AbstractAssembler<Product, ProductModelRequest, ProductModelResponse> {

	public ProductAssembler() {
		super(RestaurantProductController.class, ProductModelResponse.class);
	}

	@Override
	public Product toEntity(ProductModelRequest request) {
		return modelMapper.map(request, Product.class);
	}

	@Override
	public ProductModelResponse toModel(Product entity) {
		return modelMapper.map(entity, ProductModelResponse.class);
	}

	@Override
	public void copyToEntity(ProductModelRequest request, Product entity) {
		modelMapper.map(request, entity);
	}

}
