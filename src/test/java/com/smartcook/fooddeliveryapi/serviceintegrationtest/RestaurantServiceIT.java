package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.Address;
import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.service.CityService;
import com.smartcook.fooddeliveryapi.service.CuisineService;
import com.smartcook.fooddeliveryapi.service.RestaurantService;
import com.smartcook.fooddeliveryapi.service.StateService;

public class RestaurantServiceIT extends AbstractTransactionalServiceTest {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CuisineService cuisineService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private CityService cityService;
	
	private Restaurant habaneroBrazilianMaringa;
	private Cuisine brazilian;
	private Address address;
	
	@BeforeEach
	public void setUp() {
		Cuisine cuisine = new Cuisine();
		cuisine.setName("Brazilian");

		cuisine = cuisineService.create(cuisine);
		
		this.brazilian = cuisine;
		
		State state = new State();
		state.setName("Paraná");
		
		state = stateService.create(state);
		
		City city = new City();
		city.setState(state);
		city.setName("Maringá");
		
		city = cityService.create(city);
		
		Address address = new Address();
		address.setZipCode("87005002");
		address.setNumber("159");
		address.setComplement("Complement");
		address.setNeighborhood("Neighborhood");
		address.setCity(city);
		
		this.address = address;
		
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Habanero");
		restaurant.setFreightRate(BigDecimal.valueOf(10));
		restaurant.setCuisine(cuisine);
		restaurant.setAddress(address);
		
		this.habaneroBrazilianMaringa = restaurant;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidRestaurant() {
		restaurantService.create(habaneroBrazilianMaringa);
		assertNotNull(habaneroBrazilianMaringa.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenUnexistsACuisine() {
		Cuisine cuisine = new Cuisine();
		cuisine.setId(999L);
		cuisine.setName("Brazilian");
		
		habaneroBrazilianMaringa.setCuisine(cuisine);
		
		assertThatThrownBy(() -> {
			restaurantService.create(habaneroBrazilianMaringa);
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldFailOnCreate_WhenUnexistsACity() {
		City city = new City();
		city.setId(999L);
		city.setName("Maringá");
		
		habaneroBrazilianMaringa.getAddress().setCity(city);
		
		assertThatThrownBy(() -> {
			restaurantService.create(habaneroBrazilianMaringa);
		}).hasMessageContaining("M-7");
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsARestaurantWithTheSameName() {
		restaurantService.create(habaneroBrazilianMaringa);
		
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Habanero");
		restaurant.setFreightRate(BigDecimal.valueOf(10));
		restaurant.setCuisine(brazilian);
		restaurant.setAddress(address);
		
		assertThatThrownBy(() -> {
			restaurantService.create(habaneroBrazilianMaringa);
		}).hasMessageContaining("M-11");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllRestaurants() {
		restaurantService.create(habaneroBrazilianMaringa);
		
		List<Restaurant> list = restaurantService.findAll();
		
		assertEquals(1, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingRestaurant() {
		restaurantService.create(habaneroBrazilianMaringa);
		assertNotNull(habaneroBrazilianMaringa.getId());
		
		Restaurant result = restaurantService.findById(habaneroBrazilianMaringa.getId());
		assertEquals(habaneroBrazilianMaringa, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingRestaurant() {
		assertThatThrownBy(() -> {
			restaurantService.findById(1L);
		}).hasMessageContaining("M-10");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingRestaurant() {
		restaurantService.create(habaneroBrazilianMaringa);
		
		habaneroBrazilianMaringa.setName("Cachaçaria");
		
		restaurantService.update(habaneroBrazilianMaringa);
		
		assertEquals("Cachaçaria", habaneroBrazilianMaringa.getName());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenUnexistsACuisine() {
		restaurantService.create(habaneroBrazilianMaringa);
		
		Cuisine cuisine = new Cuisine();
		cuisine.setId(999L);
		cuisine.setName("Brazilian");
		
		habaneroBrazilianMaringa.setCuisine(cuisine);
		
		assertThatThrownBy(() -> {
			restaurantService.update(habaneroBrazilianMaringa);
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenUnexistsACity() {
		restaurantService.create(habaneroBrazilianMaringa);
		
		City city = new City();
		city.setId(999L);
		city.setName("Maringá");
		
		habaneroBrazilianMaringa.getAddress().setCity(city);
		
		assertThatThrownBy(() -> {
			restaurantService.update(habaneroBrazilianMaringa);
		}).hasMessageContaining("M-7");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsARestaurantWithTheSameName() {
		restaurantService.create(habaneroBrazilianMaringa);
		
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Cachaçaria");
		restaurant.setFreightRate(BigDecimal.valueOf(10));
		restaurant.setCuisine(brazilian);
		restaurant.setAddress(address);
		
		restaurantService.create(restaurant);
		
		restaurant.setName("Habanero");
		
		assertThatThrownBy(() -> {
			restaurantService.update(restaurant);
		}).hasMessageContaining("M-11");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingRestaurant() {
		restaurantService.create(habaneroBrazilianMaringa);
		
		restaurantService.delete(habaneroBrazilianMaringa.getId());
		
		assertThatThrownBy(() -> {
			restaurantService.findById(habaneroBrazilianMaringa.getId());
		}).hasMessageContaining("M-10");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingRestaurant() {
		assertThatThrownBy(() -> {
			restaurantService.findById(1L);
		}).hasMessageContaining("M-10");
	}
}
