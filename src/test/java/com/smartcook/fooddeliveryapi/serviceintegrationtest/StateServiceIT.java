package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.service.StateService;

public class StateServiceIT extends AbstractTransactionalServiceTest {

	@Autowired
	private StateService stateService;
	
	private State parana;
	
	@BeforeEach
	public void setUp() {
		State state = new State();
		state.setName("Paraná");
		
		this.parana = state;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidState() {
		stateService.create(parana);
		assertNotNull(parana.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAStateWithTheSameName() {
		stateService.create(parana);

		State duplicatedState = new State();
		duplicatedState.setName("Paraná");
		
		assertThatThrownBy(() -> {
			stateService.create(duplicatedState);
		}).hasMessageContaining("M-5");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllStates() {
		stateService.create(parana);

		State saoPaulo = new State();
		saoPaulo.setName("São Paulo");
		
		stateService.create(saoPaulo);

		List<State> list = stateService.findAll();
		
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingState() {
		stateService.create(parana);
		assertNotNull(parana.getId());
		
		State result = stateService.findById(parana.getId());
		assertEquals(parana, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingState() {
		assertThatThrownBy(() -> {
			stateService.findById(1L);
		}).hasMessageContaining("M-4");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingState() {
		stateService.create(parana);
		
		parana.setName("São Paulo");
		
		stateService.update(parana);
		
		assertEquals("São Paulo", parana.getName());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAStateWithTheSameName() {
		stateService.create(parana);

		State saoPaulo = new State();
		saoPaulo.setName("São Paulo");
		
		stateService.create(saoPaulo);
		
		saoPaulo.setName("Paraná");
		
		assertThatThrownBy(() -> {
			stateService.update(saoPaulo);
		}).hasMessageContaining("M-5");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingState() {
		stateService.create(parana);
		
		stateService.delete(parana.getId());
		
		assertThatThrownBy(() -> {
			stateService.findById(parana.getId());
		}).hasMessageContaining("M-4");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingState() {
		assertThatThrownBy(() -> {
			stateService.delete(1L);
		}).hasMessageContaining("M-4");
	}
	
	@Test
	public void shouldFail_WhenDeleteAStateThatHasCities() {
		// TODO
	}
}
