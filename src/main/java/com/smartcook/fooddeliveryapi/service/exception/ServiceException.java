package com.smartcook.fooddeliveryapi.service.exception;

import java.util.Arrays;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 2595931725568242058L;

	private String errorCode;
	private Object[] parameters;

	public ServiceException(String errorCode, Object... parameters) {
		super(errorCode + ": " + Arrays.toString(parameters));
		
		this.errorCode = errorCode;
		this.parameters = parameters;
	}
}
