package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class NoHandlerFoundExceptionHandler extends AbstractExceptionHandler<NoHandlerFoundException> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(NoHandlerFoundException exception) {
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message(exception.getMessage())
					.action("Check the requested URL and try again")
				.build();

		return handleErrorModelResponse(HttpStatus.NOT_FOUND, "Resource not found", Arrays.asList(detail));
	}

}
