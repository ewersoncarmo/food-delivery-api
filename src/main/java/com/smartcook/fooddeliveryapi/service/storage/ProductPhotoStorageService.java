package com.smartcook.fooddeliveryapi.service.storage;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface ProductPhotoStorageService {

	void store(Photo photo);

	String retrieve(String fileName);
	
	void remove(String fileName);
	
	default void store(String fileName, Photo photo) {
		this.store(photo);
		
		if (fileName != null) {
			this.remove(fileName);
		}
	}
	
	default String generateFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}
	
	@Builder
	@Getter
	class Photo {
		
		private String fileName;
		private InputStream inputStream;
		private String contentType;
	}
	
}
