package com.smartcook.fooddeliveryapi.domain.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ErrorDetailModelResponse {
	
	private String field;
	private String code;
	private String message;
	private String action;
}
