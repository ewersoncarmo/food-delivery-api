package com.smartcook.fooddeliveryapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class AmazonS3Config {

	@Autowired
	private AmazonS3StorageProperties amazonS3StorageProperties;
	
	@Bean
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(amazonS3StorageProperties.getAccessKey()
				,amazonS3StorageProperties.getSecretAccessKey());
		
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(amazonS3StorageProperties.getRegion())
				.build();
	}
	
}
