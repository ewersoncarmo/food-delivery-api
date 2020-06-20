package com.smartcook.fooddeliveryapi.domain.event;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductPhotoStorageEvent {

	private String fileName;
	private String existingFileName;
	private InputStream inputStream;
	private String contentType;
}
