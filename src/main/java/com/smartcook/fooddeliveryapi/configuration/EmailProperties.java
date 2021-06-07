package com.smartcook.fooddeliveryapi.configuration;

import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Setter
@Getter
@Component
@ConfigurationProperties("api.mail")
@ConditionalOnProperty(name = "api.mail.type", havingValue = "smtp")
public class EmailProperties {

	@NotNull
	private String sender;
}
