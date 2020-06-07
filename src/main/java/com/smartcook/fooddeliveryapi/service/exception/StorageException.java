package com.smartcook.fooddeliveryapi.service.exception;

import lombok.Getter;

@Getter
public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 2205136921096603069L;

	public StorageException(String message) {
		super(message);
	}
}
