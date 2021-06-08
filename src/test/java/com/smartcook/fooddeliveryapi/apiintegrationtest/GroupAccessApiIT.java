package com.smartcook.fooddeliveryapi.apiintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

class GroupAccessApiIT extends AbstractRestAssuredIntegrationTest {
	
	@Override
	protected String getBasePath() {
		return "/api/v1/groups-access";
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidGroupAccess() {
		createAValidGroupAccess()
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("Analyst"));
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAGroupAccessWithTheSameName() {
		postRequest("/json/groups-access/analyst.json");
		
		Response response = postRequest("/json/groups-access/analyst.json");
		assertServiceException(response, "M-15");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllGroupAccesss() {
		postRequest("/json/groups-access/analyst.json");
		postRequest("/json/groups-access/super-user.json");
		
		getRequest()
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data.content")
					.body("", hasSize(2))
					.body("[0].id", greaterThan(0))
					.body("[0].name", equalTo("Analyst"))
					.body("[1].id", greaterThan(0))
					.body("[1].name", equalTo("Super User"));
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingGroupAccess() {
		postRequest("/json/groups-access/analyst.json");

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		getRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("Analyst"));
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingGroupAccess() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = getRequest(pathParams, "/{id}");
		assertServiceException(response, "M-14");
	}

	@Test
	public void shouldSucceed_WhenUpdateAnExistingGroupAccess() {
		postRequest("/json/groups-access/analyst.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/groups-access/super-user.json", pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("Super User"));
	}
	
	@Test
	public void shouldFail_WhenUpdateAnUnexistingGroupAccess() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/groups-access/super-user.json", pathParams, "/{id}");
		assertServiceException(response, "M-14");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAGroupAccessWithTheSameName() {
		postRequest("/json/groups-access/analyst.json");
		postRequest("/json/groups-access/super-user.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 2);
		
		Response response = putRequest("/json/groups-access/analyst.json", pathParams, "/{id}");
		assertServiceException(response, "M-15");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingGroupAccess() {
		postRequest("/json/groups-access/analyst.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		deleteRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingGroupAccess() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-14");
	}

	// TODO 
	@Test
	public void shouldFail_WhenDeleteAGroupAccessThatHasUsers() {
	}

	public Response createAValidGroupAccess() {
		return postRequest("/json/groups-access/analyst.json");
	}
}
