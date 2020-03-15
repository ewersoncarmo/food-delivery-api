package com.smartcook.fooddeliveryapi.apiintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

class CityApiIT extends AbstractRestAssuredIntegrationTest {
	
	@Override
	protected String getBasePath() {
		return "/api/v1/cities";
	}

	@Test
	public void shouldSucceed_WhenCreateAValidCity() {
		createAValidCity()
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("Maringá"))
					.body("state", notNullValue())
					.body("state.id", greaterThan(0))
					.body("state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldFailOnCreate_WhenUnexistsAState() {
		Response response = postRequest("/json/cities/maringa-parana.json");
		assertServiceException(response, "M-4");
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsACityWithTheSameName() {
		createAValidCity();
		
		Response response = postRequest("/json/cities/maringa-parana.json");
		assertServiceException(response, "M-8");
	}

	@Test
	public void shouldSucceed_WhenFindAllCities() {
		createAValidCity();
		
		getRequest()
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("", hasSize(1))
					.body("[0].id", greaterThan(0))
					.body("[0].name", equalTo("Maringá"))
					.body("[0].state", notNullValue())
					.body("[0].state.id", greaterThan(0))
					.body("[0].state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingCity() {
		createAValidCity();

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		getRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("Maringá"))
					.body("state", notNullValue())
					.body("state.id", greaterThan(0))
					.body("state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingCity() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = getRequest(pathParams, "/{id}");
		assertServiceException(response, "M-7");
	}

	@Test
	public void shouldSucceed_WhenUpdateAnExistingCity() {
		createAValidCity();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/cities/curitiba-parana.json", pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("name", equalTo("Curitiba"))
					.body("state", notNullValue())
					.body("state.id", greaterThan(0))
					.body("state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldFail_WhenUpdateAnUnexistingCity() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/cities/presidente-venceslau-sao-paulo.json", pathParams, "/{id}");
		assertServiceException(response, "M-7");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenUnexistsAState() {
		createAValidCity();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/cities/presidente-venceslau-sao-paulo.json", pathParams, "/{id}");
		assertServiceException(response, "M-4");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsACityWithTheSameName() {
		createAValidCity();
		
		postRequest("/json/cities/curitiba-parana.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 2);
		
		Response response = putRequest("/json/cities/maringa-parana.json", pathParams, "/{id}");
		assertServiceException(response, "M-8");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingCity() {
		createAValidCity();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		deleteRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingCity() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-7");
	}

	@Test
	public void shouldFail_WhenDeleteACityThatHasRestaurants() {
		new RestaurantApiIT().createAValidRestaurant();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-9");
	}
	
	public Response createAValidCity() {
		new StateApiIT().createAValidState();
		
		return postRequest("/json/cities/maringa-parana.json");
	}
}
