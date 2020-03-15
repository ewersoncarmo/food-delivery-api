package com.smartcook.fooddeliveryapi.apiintegrationtest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import com.smartcook.fooddeliveryapi.util.AbstractConfigurationTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractRestAssuredIntegrationTest extends AbstractConfigurationTest {

	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}
	
	protected Response postRequest(String body) {
		return given()
					.spec(buildRequestSpecification(body))
					.log()
						.all()
			   .when()
			   		.post(getBasePath())
			   .thenReturn();
	}
	
	protected Response getRequest() {
		return given()
					.log()
						.all()
			   .when()
			   		.get(getBasePath())
			   .thenReturn();
	}
	
	protected Response getRequest(Map<String, Object> params, String sufix) {
		return given()
					.pathParams(params)
					.log()
						.all()
			   .when()
			   		.get(getBasePath() + sufix)
			   .thenReturn();
	}
	
	protected Response putRequest(String body, Map<String, Object> params, String sufix) {
		return given()
					.spec(buildRequestSpecification(body))
					.pathParams(params)
					.log()
						.all()
			   .when()
			   		.put(getBasePath() + sufix)
			   .thenReturn();
	}
	
	protected Response deleteRequest(Map<String, Object> params, String sufix) {
		return given()
					.pathParams(params)
					.log()
						.all()
			   .when()
			   		.delete(getBasePath() + sufix)
			   .thenReturn();
	}
	
	protected void assertServiceException(Response response, String code) {
		response
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.root("error")
					.body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
					.body("timestamp", notNullValue())
					.body("title", equalTo("Service"))
					.body("details", hasSize(1))
					.body("details[0].field", nullValue())
					.body("details[0].code", equalTo(code))
					.body("details[0].message", notNullValue())
					.body("details[0].action", nullValue());
	}
	
	protected abstract String getBasePath();

	private RequestSpecification buildRequestSpecification(String body) {
		return new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.addHeader("Accept", ContentType.JSON.toString())
				.setBody(getContentFromResource(body))
				.build();
	}
	
	private String getContentFromResource(String resourceName) {
		try {
			InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
