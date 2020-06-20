package com.smartcook.fooddeliveryapi.domain.model.dto;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

@Builder
@Getter
public class EmailMessage {

	@Singular
	private Set<String> recipients;
	
	@NonNull
	private String subject;
	
	@NonNull
	private String body;
	
	@Singular
	private Map<String, Object> variables;
}
