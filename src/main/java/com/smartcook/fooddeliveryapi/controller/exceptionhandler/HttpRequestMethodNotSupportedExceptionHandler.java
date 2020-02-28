package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class HttpRequestMethodNotSupportedExceptionHandler extends AbstractExceptionHandler<HttpRequestMethodNotSupportedException> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(HttpRequestMethodNotSupportedException exception) {
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message(exception.getMessage())
					.action(String.format("Supported methods are %s", Arrays.toString(exception.getSupportedMethods())))
				.build();

		return handleErrorModelResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed", Arrays.asList(detail));
	}

}
