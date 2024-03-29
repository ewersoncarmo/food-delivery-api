package com.smartcook.fooddeliveryapi.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "api.storage.type", havingValue = "s3")
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
