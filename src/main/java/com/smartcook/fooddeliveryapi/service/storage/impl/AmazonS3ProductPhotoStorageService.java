package com.smartcook.fooddeliveryapi.service.storage.impl;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.smartcook.fooddeliveryapi.configuration.AmazonS3StorageProperties;
import com.smartcook.fooddeliveryapi.service.exception.StorageException;
import com.smartcook.fooddeliveryapi.service.storage.ProductPhotoStorageService;

@Component
public class AmazonS3ProductPhotoStorageService implements ProductPhotoStorageService {

	private static final Logger LOG = LoggerFactory.getLogger(AmazonS3ProductPhotoStorageService.class);
	
	@Autowired
	private AmazonS3StorageProperties amazonS3StorageProperties;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Override
	public void store(Photo photo) {
		try {
			String filePath = getFilePath(photo.getFileName());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(photo.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					amazonS3StorageProperties.getBucketName(), 
					filePath, 
					photo.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			LOG.error("There was an unexpected error while storaging photo.", e);
			throw new StorageException("There was an unexpected error while storaging photo.");
		}
	}

	@Override
	public void remove(String fileName) {
		try {
			String filePath = getFilePath(fileName);

	        var deleteObjectRequest = new DeleteObjectRequest(
	        		amazonS3StorageProperties.getBucketName(), filePath);

	        amazonS3.deleteObject(deleteObjectRequest);
	    } catch (Exception e) {
	    	LOG.error("There was an unexpected error while removing photo.", e);
			throw new StorageException("There was an unexpected error while removing photo.");
	    }
	}

	@Override
	public String retrieve(String fileName) {
		String filePath = getFilePath(fileName);
		
		URL url = amazonS3.getUrl(amazonS3StorageProperties.getBucketName(), filePath);
		
		return url.toString();
	}
	
	private String getFilePath(String fileName) {
		return String.format("%s/%s", amazonS3StorageProperties.getFolder(), fileName);
	}

}
