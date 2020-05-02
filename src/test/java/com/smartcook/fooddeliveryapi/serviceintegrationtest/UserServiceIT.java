package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.service.UserService;

public class UserServiceIT extends AbstractTransactionalServiceTest {

	@Autowired
	private UserService userService;
	
	private User john;
	
	@BeforeEach
	public void setUp() {
		User user = new User();
		user.setName("John");
		user.setEmail("john@gmail.com");
		user.setPassword("john123");
		
		this.john = user;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidUser() {
		userService.create(john);
		assertNotNull(john.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAUserWithTheSameEmail() {
		userService.create(john);

		User duplicatedUser = new User();
		duplicatedUser.setName("John");
		duplicatedUser.setEmail("john@gmail.com");
		duplicatedUser.setPassword("john123");
		
		assertThatThrownBy(() -> {
			userService.create(duplicatedUser);
		}).hasMessageContaining("M-18");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllUsers() {
		userService.create(john);

		User paul = new User();
		paul.setName("Paul");
		paul.setEmail("paul@gmail.com");
		paul.setPassword("paul123");
		
		userService.create(paul);

		List<User> list = userService.findAll();
		
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingUser() {
		userService.create(john);
		assertNotNull(john.getId());
		
		User result = userService.findById(john.getId());
		assertEquals(john, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingUser() {
		assertThatThrownBy(() -> {
			userService.findById(1L);
		}).hasMessageContaining("M-17");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingUser() {
		userService.create(john);
		
		john.setName("Paul");
		john.setEmail("paul@gmail.com");
		
		userService.update(john);
		
		assertEquals("Paul", john.getName());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAUserWithTheSameEmail() {
		userService.create(john);

		User paul = new User();
		paul.setName("Paul");
		paul.setEmail("paul@gmail.com");
		paul.setPassword("paul123");
		
		userService.create(paul);
		
		paul.setEmail("john@gmail.com");
		
		assertThatThrownBy(() -> {
			userService.update(paul);
		}).hasMessageContaining("M-18");
	}
	
	@Test
	public void shouldFail_WhenChangesPassword() {
		User user = userService.create(john);

		assertThatThrownBy(() -> {
			userService.changePassword(user.getId(), "invalid-password", "john456");
		}).hasMessageContaining("M-20");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingUser() {
		userService.create(john);
		
		userService.delete(john.getId());
		
		assertThatThrownBy(() -> {
			userService.findById(john.getId());
		}).hasMessageContaining("M-17");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingUser() {
		assertThatThrownBy(() -> {
			userService.findById(1L);
		}).hasMessageContaining("M-17");
	}
}
