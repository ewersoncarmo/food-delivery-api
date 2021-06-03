package com.smartcook.fooddeliveryapi.applicationcontexttest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@AutoConfigureTestDatabase
class FoodDeliveryApiApplicationTests {

	@Test
	void contextLoads() {
	}
	
}
