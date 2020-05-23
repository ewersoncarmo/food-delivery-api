package com.smartcook.fooddeliveryapi.service.exception;

import lombok.Getter;

@Getter
public class ReportException extends RuntimeException {

	private static final long serialVersionUID = -764576417474581012L;

	public ReportException(String message) {
		super(message);
	}
}
