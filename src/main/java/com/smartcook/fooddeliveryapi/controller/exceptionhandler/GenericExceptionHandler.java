package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class GenericExceptionHandler extends AbstractExceptionHandler<Exception> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(Exception exception) {
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message(exception.getMessage())
					.action("Check the application log file for more details")
				.build();

		return handleErrorModelResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", Arrays.asList(detail));
	}

}
