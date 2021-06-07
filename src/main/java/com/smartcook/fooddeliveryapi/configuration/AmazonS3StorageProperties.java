package com.smartcook.fooddeliveryapi.configuration;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("api.storage.s3")
@ConditionalOnProperty(name = "api.storage.type", havingValue = "s3")
public class AmazonS3StorageProperties {

	private String accessKey;
	private String secretAccessKey;
	private String bucketName;
	private Regions region;
	private String folder;
}
