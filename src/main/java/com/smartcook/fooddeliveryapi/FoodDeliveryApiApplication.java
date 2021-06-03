package com.smartcook.fooddeliveryapi;

import java.util.TimeZone;

import com.smartcook.fooddeliveryapi.configuration.security.Base64ProtocolResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodDeliveryApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication springApplication = new SpringApplication(FoodDeliveryApiApplication.class);
		springApplication.addListeners(new Base64ProtocolResolver());
		springApplication.run(args);
	}

}
