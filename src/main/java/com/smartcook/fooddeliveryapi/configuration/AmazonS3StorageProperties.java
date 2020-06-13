package com.smartcook.fooddeliveryapi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("api.storage.s3")
public class AmazonS3StorageProperties {

	private String accessKey;
	private String secretAccessKey;
	private String bucketName;
	private Regions region;
	private String folder;
}
