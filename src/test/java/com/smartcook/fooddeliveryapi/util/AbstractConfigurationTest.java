package com.smartcook.fooddeliveryapi.util;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "/application-test.properties")
@AutoConfigureTestDatabase
public abstract class AbstractConfigurationTest {

	
}
