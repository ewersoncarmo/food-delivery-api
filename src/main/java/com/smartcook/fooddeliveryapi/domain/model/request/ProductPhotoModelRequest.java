package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.smartcook.fooddeliveryapi.domain.validation.FileContentType;
import com.smartcook.fooddeliveryapi.domain.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPhotoModelRequest {

	@NotNull
	@FileSize(max = "500KB", message = "The file must have a maximum size of 500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }, message = "The file must be JPG or PNG")
	private MultipartFile file;
	
	@NotBlank
	private String description;
	
}
