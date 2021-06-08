package com.smartcook.fooddeliveryapi.apiintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.Response;

class PaymentMethodApiIT extends AbstractRestAssuredIntegrationTest {
	
	@Override
	protected String getBasePath() {
		return "/api/v1/payment-methods";
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidPaymentMethod() {
		createAValidPaymentMethod()
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.root("data")
					.body("id", greaterThan(0))
					.body("description", equalTo("Credit Card"));
	}

	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAPaymentMethodWithTheSameName() {
		postRequest("/json/payment-methods/credit-card.json");
		
		Response response = postRequest("/json/payment-methods/credit-card.json");
		assertServiceException(response, "M-13");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllPaymentMethods() {
		postRequest("/json/payment-methods/credit-card.json");
		postRequest("/json/payment-methods/debit-card.json");
		
		getRequest()
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data.content")
					.body("", hasSize(2))
					.body("[0].id", greaterThan(0))
					.body("[0].description", equalTo("Credit Card"))
					.body("[1].id", greaterThan(0))
					.body("[1].description", equalTo("Debit Card"));
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingPaymentMethod() {
		postRequest("/json/payment-methods/credit-card.json");

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		getRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("description", equalTo("Credit Card"));
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingPaymentMethod() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = getRequest(pathParams, "/{id}");
		assertServiceException(response, "M-12");
	}

	@Test
	public void shouldSucceed_WhenUpdateAnExistingPaymentMethod() {
		postRequest("/json/payment-methods/credit-card.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		putRequest("/json/payment-methods/debit-card.json", pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.root("data")
					.body("id", equalTo(1))
					.body("description", equalTo("Debit Card"));
	}
	
	@Test
	public void shouldFail_WhenUpdateAnUnexistingPaymentMethod() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = putRequest("/json/payment-methods/debit-card.json", pathParams, "/{id}");
		assertServiceException(response, "M-12");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAPaymentMethodWithTheSameName() {
		postRequest("/json/payment-methods/credit-card.json");
		postRequest("/json/payment-methods/debit-card.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 2);
		
		Response response = putRequest("/json/payment-methods/credit-card.json", pathParams, "/{id}");
		assertServiceException(response, "M-13");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingPaymentMethod() {
		postRequest("/json/payment-methods/credit-card.json");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		deleteRequest(pathParams, "/{id}")
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingPaymentMethod() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("id", 1);
		
		Response response = deleteRequest(pathParams, "/{id}");
		assertServiceException(response, "M-12");
	}
	
	// TODO - check if payment methods has relationships before removing
	
	public Response createAValidPaymentMethod() {
		return postRequest("/json/payment-methods/credit-card.json");
	}
}
