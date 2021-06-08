package com.smartcook.fooddeliveryapi.apiintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

class CuisineApiIT extends AbstractRestAssuredIntegrationTest {
	
	@Override
	protected String getBasePath() {
		return "/api/v1/cuisines";
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidCuisine() {
		createAValidCuisine()
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("Brazilian"));
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsACuisineWithTheSameName() {
		postRequest("/json/cuisines/brazilian.json");
		
		Response response = postRequest("/json/cuisines/brazilian.json");
		assertServiceException(response, "M-2");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllCuisines() {
		postRequest("/json/cuisines/brazilian.json");
		postRequest("/json/cuisines/italian.json");
		
		getRequest()
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data.content")
					.body("", hasSize(2))
					.body("[0].id", greaterThan(0))
					.body("[0].name", equalTo("Brazilian"))
					.body("[1].id", greaterThan(0))
					.body("[1].name", equalTo("Italian"));
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingCuisine() {
		postRequest("/json/cuisines/brazilian.json");

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		getRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("Brazilian"));
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingCuisine() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = getRequest(pathParams, "/{id}");
		assertServiceException(response, "M-1");
	}

	@Test
	public void shouldSucceed_WhenUpdateAnExistingCuisine() {
		postRequest("/json/cuisines/brazilian.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/cuisines/italian.json", pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("Italian"));
	}
	
	@Test
	public void shouldFail_WhenUpdateAnUnexistingCuisine() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/cuisines/italian.json", pathParams, "/{id}");
		assertServiceException(response, "M-1");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsACuisineWithTheSameName() {
		postRequest("/json/cuisines/brazilian.json");
		postRequest("/json/cuisines/italian.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 2);
		
		Response response = putRequest("/json/cuisines/brazilian.json", pathParams, "/{id}");
		assertServiceException(response, "M-2");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingCuisine() {
		postRequest("/json/cuisines/brazilian.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		deleteRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingCuisine() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-1");
	}

	@Test
	public void shouldFail_WhenDeleteACuisineThatHasRestaurants() {
		new RestaurantApiIT().createAValidRestaurant();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-3");
	}

	public Response createAValidCuisine() {
		return postRequest("/json/cuisines/brazilian.json");
	}
}
