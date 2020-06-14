package com.smartcook.fooddeliveryapi.service.storage.impl;

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
