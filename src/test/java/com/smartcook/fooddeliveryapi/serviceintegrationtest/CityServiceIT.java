package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.service.CityService;
import com.smartcook.fooddeliveryapi.service.StateService;

public class CityServiceIT extends AbstractTransactionalServiceTest {

	@Autowired
	private StateService stateService;
	
	@Autowired
	private CityService cityService;
	
	private State parana;
	private City maringaParana;
	
	@BeforeEach
	public void setUp() {
		State state = new State();
		state.setName("Paraná");
		
		this.parana = stateService.create(state);
		
		City city = new City();
		city.setState(state);
		city.setName("Maringá");
		
		this.maringaParana = city;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidCity() {
		cityService.create(maringaParana);
		assertNotNull(maringaParana.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenUnexistsAState() {
		State saoPaulo = new State();
		saoPaulo.setId(999L);
		saoPaulo.setName("São Paulo");
		
		maringaParana.setState(saoPaulo);
		
		assertThatThrownBy(() -> {
			cityService.create(maringaParana);
		}).hasMessageContaining("M-4");
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsACityWithTheSameName() {
		cityService.create(maringaParana);
		
		City duplicatedCity = new City();
		duplicatedCity.setState(parana);
		duplicatedCity.setName("Maringá");
		
		assertThatThrownBy(() -> {
			cityService.create(duplicatedCity);
		}).hasMessageContaining("M-8");
	}
	
	@Test
	public void shouldSucceedOnCreate_WhenExistsACityWithTheSameNameWithADifferentState() {
		cityService.create(maringaParana);
		
		State saoPaulo = new State();
		saoPaulo.setName("São Paulo");
		
		stateService.create(saoPaulo);
		
		City maringaSaoPaulo = new City();
		maringaSaoPaulo.setState(saoPaulo);
		maringaSaoPaulo.setName("Maringá");
		
		cityService.create(maringaSaoPaulo);
		assertNotNull(maringaSaoPaulo.getId());
	}
	
	@Test
	public void shouldSucceed_WhenFindAllCities() {
		cityService.create(maringaParana);
		
		City curitibaParana = new City();
		curitibaParana.setState(parana);
		curitibaParana.setName("Curitiba");

		cityService.create(curitibaParana);
		
		List<City> list = cityService.findAll();
		
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingCity() {
		cityService.create(maringaParana);
		assertNotNull(maringaParana.getId());
		
		City result = cityService.findById(maringaParana.getId());
		assertEquals(maringaParana, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingCity() {
		assertThatThrownBy(() -> {
			cityService.findById(1L);
		}).hasMessageContaining("M-7");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingCity() {
		cityService.create(maringaParana);
		
		maringaParana.setName("Curitiba");
		
		cityService.update(maringaParana);
		
		assertEquals("Curitiba", maringaParana.getName());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenUnexistsAState() {
		cityService.create(maringaParana);
		
		State saoPaulo = new State();
		saoPaulo.setId(999L);
		saoPaulo.setName("São Paulo");
		
		maringaParana.setState(saoPaulo);
		
		assertThatThrownBy(() -> {
			cityService.update(maringaParana);
		}).hasMessageContaining("M-4");
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsACityWithTheSameName() {
		cityService.create(maringaParana);
		
		City curitibaParana = new City();
		curitibaParana.setState(parana);
		curitibaParana.setName("Curitiba");
		
		cityService.create(curitibaParana);
		
		curitibaParana.setName("Maringá");

		assertThatThrownBy(() -> {
			cityService.update(curitibaParana);
		}).hasMessageContaining("M-8");
	}
	
	@Test
	public void shouldSucceedOnUpdate_WhenExistsACityWithTheSameNameWithADifferentState() {
		cityService.create(maringaParana);
		
		City curitibaParana = new City();
		curitibaParana.setState(parana);
		curitibaParana.setName("Curitiba");
		
		cityService.create(curitibaParana);
		
		State saoPaulo = new State();
		saoPaulo.setName("São Paulo");
		
		stateService.create(saoPaulo);
		
		curitibaParana.setState(saoPaulo);
		curitibaParana.setName("Maringá");

		assertNotNull(curitibaParana.getId());
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingCity() {
		cityService.create(maringaParana);
		
		cityService.delete(maringaParana.getId());
		
		assertThatThrownBy(() -> {
			cityService.findById(maringaParana.getId());
		}).hasMessageContaining("M-7");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingCity() {
		assertThatThrownBy(() -> {
			cityService.findById(1L);
		}).hasMessageContaining("M-7");
	}
}
