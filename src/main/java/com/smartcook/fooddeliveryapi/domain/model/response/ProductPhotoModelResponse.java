package com.smartcook.fooddeliveryapi.domain.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPhotoModelResponse extends RepresentationModel<ProductPhotoModelResponse> {

	private String fileName;
	private String description;
	private String contentType;
	private Long size;
	
}
