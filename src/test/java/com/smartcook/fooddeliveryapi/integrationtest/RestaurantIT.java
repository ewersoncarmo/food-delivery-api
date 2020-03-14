package com.smartcook.fooddeliveryapi.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

class RestaurantIT extends AbstractRestAssuredIntegrationTest {
	
	@Override
	protected String getBasePath() {
		return "/api/v1/restaurants";
	}

	@Test
	public void shouldSucceed_WhenCreateAValidRestaurant() {
		createAValidRestaurant()
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("Habanero"))
					.body("freightRate", equalTo(10))
					.body("cuisine.id", equalTo(1))
					.body("cuisine.name", equalTo("Brazilian"))
					.body("address.zipCode", equalTo("87005002"))
					.body("address.street", equalTo("Street"))
					.body("address.number", equalTo("159"))
					.body("address.complement", equalTo("Complement"))
					.body("address.neighborhood", equalTo("Neighborhood"))
					.body("address.city.id", equalTo(1))
					.body("address.city.name", equalTo("Maringá"))
					.body("address.city.state.id", equalTo(1))
					.body("address.city.state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldFailOnCreate_WhenUnexistsACuisine() {
		new CityIT().createAValidCity();
		
		Response response = postRequest("/json/restaurants/habanero-brazilian-maringa.json");
		assertServiceException(response, "M-1");
	}
	
	@Test
	public void shouldFailOnCreate_WhenUnexistsACity() {
		new CuisineIT().createAValidCuisine();
		
		Response response = postRequest("/json/restaurants/habanero-brazilian-maringa.json");
		assertServiceException(response, "M-7");
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsARestaurantWithTheSameName() {
		createAValidRestaurant();
		
		Response response = postRequest("/json/restaurants/habanero-brazilian-maringa.json");
		assertServiceException(response, "M-11");
	}

	@Test
	public void shouldSucceed_WhenFindAllRestaurants() {
		createAValidRestaurant();
		
		getRequest()
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("", hasSize(1))
					.body("[0].id", greaterThan(0))
					.body("[0].name", equalTo("Habanero"))
					.body("[0].freightRate", equalTo(BigDecimal.valueOf(10).floatValue()))
					.body("[0].cuisine.id", equalTo(1))
					.body("[0].cuisine.name", equalTo("Brazilian"))
					.body("[0].address.zipCode", equalTo("87005002"))
					.body("[0].address.street", equalTo("Street"))
					.body("[0].address.number", equalTo("159"))
					.body("[0].address.complement", equalTo("Complement"))
					.body("[0].address.neighborhood", equalTo("Neighborhood"))
					.body("[0].address.city.id", equalTo(1))
					.body("[0].address.city.name", equalTo("Maringá"))
					.body("[0].address.city.state.id", equalTo(1))
					.body("[0].address.city.state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingRestaurant() {
		createAValidRestaurant();

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		getRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("Habanero"))
					.body("freightRate", equalTo(BigDecimal.valueOf(10).floatValue()))
					.body("cuisine.id", equalTo(1))
					.body("cuisine.name", equalTo("Brazilian"))
					.body("address.zipCode", equalTo("87005002"))
					.body("address.street", equalTo("Street"))
					.body("address.number", equalTo("159"))
					.body("address.complement", equalTo("Complement"))
					.body("address.neighborhood", equalTo("Neighborhood"))
					.body("address.city.id", equalTo(1))
					.body("address.city.name", equalTo("Maringá"))
					.body("address.city.state.id", equalTo(1))
					.body("address.city.state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingRestaurant() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = getRequest(pathParams, "/{id}");
		assertServiceException(response, "M-10");
	}

	@Test
	public void shouldSucceed_WhenUpdateAnExistingRestaurant() {
		createAValidRestaurant();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/restaurants/cachacaria-brazilian-maringa.json", pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("name", equalTo("Cachaçaria"))
					.body("freightRate", equalTo(20))
					.body("cuisine.id", equalTo(1))
					.body("cuisine.name", equalTo("Brazilian"))
					.body("address.zipCode", equalTo("84265400"))
					.body("address.street", equalTo("New Street"))
					.body("address.number", equalTo("200"))
					.body("address.complement", equalTo("New Complement"))
					.body("address.neighborhood", equalTo("New Neighborhood"))
					.body("address.city.id", equalTo(1))
					.body("address.city.name", equalTo("Maringá"))
					.body("address.city.state.id", equalTo(1))
					.body("address.city.state.name", equalTo("Paraná"));
	}
	
	@Test
	public void shouldFail_WhenUpdateAnUnexistingRestaurant() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/restaurants/cachacaria-brazilian-maringa.json", pathParams, "/{id}");
		assertServiceException(response, "M-10");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenUnexistsACuisine() {
		createAValidRestaurant();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/restaurants/hoyama-japanese-maringa.json", pathParams, "/{id}");
		assertServiceException(response, "M-1");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenUnexistsACity() {
		createAValidRestaurant();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/restaurants/habanero-brazilian-presidente-venceslau.json", pathParams, "/{id}");
		assertServiceException(response, "M-7");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsARestaurantWithTheSameName() {
		createAValidRestaurant();
		
		postRequest("/json/restaurants/cachacaria-brazilian-maringa.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 2);
		
		Response response = putRequest("/json/restaurants/habanero-brazilian-maringa.json", pathParams, "/{id}");
		assertServiceException(response, "M-11");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingRestaurant() {
		createAValidRestaurant();
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		deleteRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingRestaurant() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-10");
	}
	
	// TODO - check if restaurants has relationships before removing
	
	public Response createAValidRestaurant() {
		new CuisineIT().createAValidCuisine();
		new CityIT().createAValidCity();
		
		return postRequest("/json/restaurants/habanero-brazilian-maringa.json");
	}
}
