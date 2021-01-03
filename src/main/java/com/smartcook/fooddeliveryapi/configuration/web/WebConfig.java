package com.smartcook.fooddeliveryapi.configuration.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

//@Configuration
public class WebConfig {

	@Bean
	public Filter shallowEtagHeaderFilterr() {
		return new ShallowEtagHeaderFilter();
	}
}
