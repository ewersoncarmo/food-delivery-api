package com.smartcook.fooddeliveryapi.apiintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

class UserApiIT extends AbstractRestAssuredIntegrationTest {
	
	@Override
	protected String getBasePath() {
		return "/api/v1/users";
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidUser() {
		createAValidUser()
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("John"))
					.body("email", equalTo("john@gmail.com"));
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAUserWithTheSameEmail() {
		postRequest("/json/users/john.json");
		
		Response response = postRequest("/json/users/john.json");
		assertServiceException(response, "M-18");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllUsers() {
		postRequest("/json/users/john.json");
		postRequest("/json/users/paul.json");
		
		getRequest()
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("", hasSize(2))
					.body("[0].id", greaterThan(0))
					.body("[0].name", equalTo("John"))
					.body("[0].email", equalTo("john@gmail.com"))
					.body("[1].id", greaterThan(0))
					.body("[1].name", equalTo("Paul"))
					.body("[1].email", equalTo("paul@gmail.com"));
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingUser() {
		postRequest("/json/users/john.json");

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		getRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("John"))
					.body("email", equalTo("john@gmail.com"));
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingUser() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = getRequest(pathParams, "/{id}");
		assertServiceException(response, "M-17");
	}

	@Test
	public void shouldSucceed_WhenUpdateAnExistingUser() {
		postRequest("/json/users/john.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/users/john-smith.json", pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("John Smith"))
					.body("email", equalTo("john.smith@gmail.com"));
	}
	
	@Test
	public void shouldFail_WhenUpdateAnUnexistingUser() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/users/john-smith.json", pathParams, "/{id}");
		assertServiceException(response, "M-17");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAUserWithTheSameEmail() {
		postRequest("/json/users/john.json");
		postRequest("/json/users/paul.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 2);
		
		Response response = putRequest("/json/users/paul-roger.json", pathParams, "/{id}");
		assertServiceException(response, "M-18");
	}
	
	@Test
	public void shouldSucceed_WhenChangesPassword() {
		postRequest("/json/users/john.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/users/john-valid-change-password.json", pathParams, "/{id}/change-password")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenChangesPassword() {
		postRequest("/json/users/john.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/users/john-invalid-change-password.json", pathParams, "/{id}/change-password");
		assertServiceException(response, "M-20");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingUser() {
		postRequest("/json/users/john.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		deleteRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingUser() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-17");
	}

	// TODO
	@Test
	public void shouldFail_WhenDeleteAUserThatHasPurchaseOrders() {
	}

	public Response createAValidUser() {
		return postRequest("/json/users/john.json");
	}
}
