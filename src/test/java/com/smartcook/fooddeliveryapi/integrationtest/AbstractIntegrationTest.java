package com.smartcook.fooddeliveryapi.integrationtest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

@TestPropertySource(locations = "/application-test.properties")
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

	protected String getContentFromResource(String resourceName) {
		try {
			InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
