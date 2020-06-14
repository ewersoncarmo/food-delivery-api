package com.smartcook.fooddeliveryapi.service.storage;

import java.util.UUID;

import org.springframework.context.event.EventListener;

import com.smartcook.fooddeliveryapi.service.storage.impl.ProductPhotoStorageEvent;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractProductPhotoStorage {

	public static String generateFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}
	
	@EventListener
	protected void save(ProductPhotoStorageEvent event) {
		this.store(event);
		
		if (event.getExistingFileName() != null) {
			this.remove(event.getExistingFileName());
		}
	}

	protected abstract void store(ProductPhotoStorageEvent event);

	public abstract String retrieve(String fileName);
	
	public abstract void remove(String fileName2);

}
