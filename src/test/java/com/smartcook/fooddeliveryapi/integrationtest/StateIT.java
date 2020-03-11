package com.smartcook.fooddeliveryapi.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

class StateIT extends AbstractRestAssuredIntegrationTest {
	
	@Override
	protected String getBasePath() {
		return "/api/v1/states";
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidState() {
		createAValidState()
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("Paraná"));
	}

	public Response createAValidState() {
		return postRequest("/json/states/parana-state-request.json");
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAStateWithTheSameName() {
		postRequest("/json/states/parana-state-request.json");
		
		Response response = postRequest("/json/states/parana-state-request.json");
		assertServiceException(response, "M-5");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllStates() {
		postRequest("/json/states/parana-state-request.json");
		postRequest("/json/states/sao-paulo-state-request.json");
		
		getRequest()
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("", hasSize(2))
					.body("[0].id", greaterThan(0))
					.body("[0].name", equalTo("Paraná"))
					.body("[1].id", greaterThan(0))
					.body("[1].name", equalTo("São Paulo"));
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingState() {
		postRequest("/json/states/parana-state-request.json");

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		getRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingState() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = getRequest(pathParams, "/{id}");
		assertServiceException(response, "M-4");
	}

	@Test
	public void shouldSucceed_WhenUpdateAnExistingState() {
		postRequest("/json/states/parana-state-request.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/states/sao-paulo-state-request.json", pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("São Paulo"));
	}
	
	@Test
	public void shouldFail_WhenUpdateAnUnexistingState() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/states/sao-paulo-state-request.json", pathParams, "/{id}");
		assertServiceException(response, "M-4");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAStateWithTheSameName() {
		postRequest("/json/states/parana-state-request.json");
		postRequest("/json/states/sao-paulo-state-request.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 2);
		
		Response response = putRequest("/json/states/parana-state-request.json", pathParams, "/{id}");
		assertServiceException(response, "M-5");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingState() {
		postRequest("/json/states/parana-state-request.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		deleteRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingState() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-4");
	}
	
	// TODO - shouldFail_WhenDeleteAStateThatHasCities
	
}
