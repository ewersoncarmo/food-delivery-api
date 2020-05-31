package com.smartcook.fooddeliveryapi.domain.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPhotoModelResponse {

	private String fileName;
	private String description;
	private String contentType;
	private Long size;
	
}
