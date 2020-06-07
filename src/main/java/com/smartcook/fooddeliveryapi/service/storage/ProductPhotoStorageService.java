package com.smartcook.fooddeliveryapi.service.storage;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface ProductPhotoStorageService {

	void store(Photo photo);
	
	void remove(String fileName);
	
	InputStream retrieve(String fileName);
	
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
	}
}
