package com.smartcook.fooddeliveryapi.domain.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.entity.Product;
import com.smartcook.fooddeliveryapi.domain.model.request.ProductModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ProductModelResponse;

@Component
public class ProductAssembler extends AbstractAssembler<Product, ProductModelRequest, ProductModelResponse> {

	@Override
	public Product toEntity(ProductModelRequest request) {
		return modelMapper.map(request, Product.class);
	}

	@Override
	public ProductModelResponse toModel(Product entity) {
		return modelMapper.map(entity, ProductModelResponse.class);
	}

	@Override
	public List<ProductModelResponse> toCollectionModel(List<Product> entityList) {
		return entityList.stream()
				.map(entity -> toModel(entity))
				.collect(Collectors.toList());
	}

	@Override
	public void copyToEntity(ProductModelRequest request, Product entity) {
		modelMapper.map(request, entity);
	}

}
