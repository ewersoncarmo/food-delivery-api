package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;
import com.smartcook.fooddeliveryapi.service.GroupAccessService;

public class GroupAccessServiceIT extends AbstractTransactionalServiceTest {

	@Autowired
	private GroupAccessService groupAccessService;
	
	private GroupAccess analyst;
	
	@BeforeEach
	public void setUp() {
		GroupAccess groupAccess = new GroupAccess();
		groupAccess.setName("Analyst");
		
		this.analyst = groupAccess;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidGroupAccess() {
		groupAccessService.create(analyst);
		assertNotNull(analyst.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAGroupAccessWithTheSameName() {
		groupAccessService.create(analyst);

		GroupAccess duplicatedGroupAccess = new GroupAccess();
		duplicatedGroupAccess.setName("Analyst");
		
		assertThatThrownBy(() -> {
			groupAccessService.create(duplicatedGroupAccess);
		}).hasMessageContaining("M-15");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllGroupAccesss() {
		groupAccessService.create(analyst);

		GroupAccess superUser = new GroupAccess();
		superUser.setName("Super User");
		
		groupAccessService.create(superUser);

		List<GroupAccess> list = groupAccessService.findAll();
		
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingGroupAccess() {
		groupAccessService.create(analyst);
		assertNotNull(analyst.getId());
		
		GroupAccess result = groupAccessService.findById(analyst.getId());
		assertEquals(analyst, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingGroupAccess() {
		assertThatThrownBy(() -> {
			groupAccessService.findById(1L);
		}).hasMessageContaining("M-14");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingGroupAccess() {
		groupAccessService.create(analyst);
		
		analyst.setName("Super User");
		
		groupAccessService.update(analyst);
		
		assertEquals("Super User", analyst.getName());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAGroupAccessWithTheSameName() {
		groupAccessService.create(analyst);

		GroupAccess superUser = new GroupAccess();
		superUser.setName("Super User");
		
		groupAccessService.create(superUser);
		
		superUser.setName("Analyst");
		
		assertThatThrownBy(() -> {
			groupAccessService.update(superUser);
		}).hasMessageContaining("M-15");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingGroupAccess() {
		groupAccessService.create(analyst);
		
		groupAccessService.delete(analyst.getId());
		
		assertThatThrownBy(() -> {
			groupAccessService.findById(analyst.getId());
		}).hasMessageContaining("M-1");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingGroupAccess() {
		assertThatThrownBy(() -> {
			groupAccessService.findById(1L);
		}).hasMessageContaining("M-14");
	}
}
