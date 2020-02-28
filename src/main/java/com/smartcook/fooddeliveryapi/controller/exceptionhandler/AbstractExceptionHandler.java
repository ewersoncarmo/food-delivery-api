package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ErrorModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

public abstract class AbstractExceptionHandler<T extends Exception> {

	protected abstract ResponseEntity<ModelResponse<Object>> handleException(T exception);
	
	protected ResponseEntity<ModelResponse<Object>> handleErrorModelResponse(HttpStatus status, String title, List<ErrorDetailModelResponse> details) {
		ErrorModelResponse error = ErrorModelResponse
				.builder()
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.title(title)
					.details(details)
				.build();
		
		return ResponseEntity
				.status(status)
				.body(ModelResponse.withError(error));
	}
}
