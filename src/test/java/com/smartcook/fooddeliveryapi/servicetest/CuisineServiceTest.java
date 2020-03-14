package com.smartcook.fooddeliveryapi.servicetest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.service.CuisineService;

public class CuisineServiceTest extends AbstractTransactionalServiceTest {

	@Autowired
	private CuisineService cuisineService;
	
	private Cuisine cuisine;
	
	@BeforeEach
	public void setUp() {
		Cuisine cuisine = new Cuisine();
		cuisine.setName("Brazilian");
		
		this.cuisine = cuisine;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidCuisine() {
		cuisineService.create(cuisine);
		assertNotNull(cuisine.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsACuisineWithTheSameName() {
		cuisineService.create(cuisine);

		Cuisine duplicatedCuisine = new Cuisine();
		duplicatedCuisine.setName("Brazilian");
		
		assertThatThrownBy(() -> {
			cuisineService.create(duplicatedCuisine);
		}).hasMessageContaining("M-2");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllCuisines() {
		cuisineService.create(cuisine);

		Cuisine italian = new Cuisine();
		italian.setName("Italian");
		
		cuisineService.create(italian);

		List<Cuisine> list = cuisineService.findAll();
		
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingCuisine() {
		cuisineService.create(cuisine);
		assertNotNull(cuisine.getId());
		
		Cuisine result = cuisineService.findById(cuisine.getId());
		assertEquals(cuisine, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingCuisine() {
		assertThatThrownBy(() -> {
			cuisineService.findById(1L);
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingCuisine() {
		cuisineService.create(cuisine);
		
		cuisine.setName("Italian");
		
		cuisineService.update(cuisine);
		
		assertEquals("Italian", cuisine.getName());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsACuisineWithTheSameName() {
		cuisineService.create(cuisine);

		Cuisine italian = new Cuisine();
		italian.setName("Italian");
		
		cuisineService.create(italian);
		italian.setName("Brazilian");
		
		assertThatThrownBy(() -> {
			cuisineService.update(italian);
		}).hasMessageContaining("M-2");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingCuisine() {
		cuisineService.create(cuisine);
		
		cuisineService.delete(cuisine.getId());
		
		assertThatThrownBy(() -> {
			cuisineService.findById(cuisine.getId());
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingCuisine() {
		assertThatThrownBy(() -> {
			cuisineService.findById(1L);
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldFail_WhenDeleteACuisineThatHasRestaurants() {
		// TODO
	}
}
