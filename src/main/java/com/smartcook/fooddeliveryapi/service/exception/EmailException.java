package com.smartcook.fooddeliveryapi.service.exception;

import lombok.Getter;

@Getter
public class EmailException extends RuntimeException {

	private static final long serialVersionUID = -1568915267922123223L;

	public EmailException(String message) {
		super(message);
	}
}
