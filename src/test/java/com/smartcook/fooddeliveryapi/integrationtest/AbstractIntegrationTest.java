package com.smartcook.fooddeliveryapi.integrationtest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTest {

	@LocalServerPort
	protected int port;
	
	protected String getContentFromResource(String resourceName) {
		try {
			InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
