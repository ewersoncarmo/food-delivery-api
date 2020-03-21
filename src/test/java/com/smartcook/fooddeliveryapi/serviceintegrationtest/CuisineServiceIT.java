package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.service.CuisineService;

public class CuisineServiceIT extends AbstractTransactionalServiceTest {

	@Autowired
	private CuisineService cuisineService;
	
	private Cuisine brazilian;
	
	@BeforeEach
	public void setUp() {
		Cuisine cuisine = new Cuisine();
		cuisine.setName("Brazilian");
		
		this.brazilian = cuisine;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidCuisine() {
		cuisineService.create(brazilian);
		assertNotNull(brazilian.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsACuisineWithTheSameName() {
		cuisineService.create(brazilian);

		Cuisine duplicatedCuisine = new Cuisine();
		duplicatedCuisine.setName("Brazilian");
		
		assertThatThrownBy(() -> {
			cuisineService.create(duplicatedCuisine);
		}).hasMessageContaining("M-2");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllCuisines() {
		cuisineService.create(brazilian);

		Cuisine italian = new Cuisine();
		italian.setName("Italian");
		
		cuisineService.create(italian);

		List<Cuisine> list = cuisineService.findAll();
		
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingCuisine() {
		cuisineService.create(brazilian);
		assertNotNull(brazilian.getId());
		
		Cuisine result = cuisineService.findById(brazilian.getId());
		assertEquals(brazilian, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingCuisine() {
		assertThatThrownBy(() -> {
			cuisineService.findById(1L);
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingCuisine() {
		cuisineService.create(brazilian);
		
		brazilian.setName("Italian");
		
		cuisineService.update(brazilian);
		
		assertEquals("Italian", brazilian.getName());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsACuisineWithTheSameName() {
		cuisineService.create(brazilian);

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
		cuisineService.create(brazilian);
		
		cuisineService.delete(brazilian.getId());
		
		assertThatThrownBy(() -> {
			cuisineService.findById(brazilian.getId());
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingCuisine() {
		assertThatThrownBy(() -> {
			cuisineService.findById(1L);
		}).hasMessageContaining("M-1");
	}
}
