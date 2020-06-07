package com.smartcook.fooddeliveryapi.service.storage.impl;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.smartcook.fooddeliveryapi.service.exception.StorageException;
import com.smartcook.fooddeliveryapi.service.storage.ProductPhotoStorageService;

@Service
public class LocalProductPhotoStorageService implements ProductPhotoStorageService {

	private static final Logger LOG = LoggerFactory.getLogger(LocalProductPhotoStorageService.class);
	
	@Value("${api.storage.local.folder}")
	private Path folder;
	
	@Override
	public void store(Photo photo) {
		try {
			Path filePath = getFilePath(photo.getFileName());
			
			FileCopyUtils.copy(photo.getInputStream(), Files.newOutputStream(filePath));
		} catch (Exception e) {
			LOG.error("There was an unexpected error while storaging photo.", e);
			throw new StorageException("There was an unexpected error while storaging photo.");
		}
	}

	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			LOG.error("There was an unexpected error while removing photo.", e);
			throw new StorageException("There was an unexpected error while removing photo.");
		}
	}
	
	@Override
	public InputStream retrieve(String fileName) {
		try {
	        Path filePath = getFilePath(fileName);
	        return Files.newInputStream(filePath);
	    } catch (Exception e) {
	    	LOG.error("There was an unexpected error while retrieving photo.", e);
			throw new StorageException("There was an unexpected error while retrieving photo.");
	    }
	}

	private Path getFilePath(String fileName) {
		return folder.resolve(Path.of(fileName));
	}

}
