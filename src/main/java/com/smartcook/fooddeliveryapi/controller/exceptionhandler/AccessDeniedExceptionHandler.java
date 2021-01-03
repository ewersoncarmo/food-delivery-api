package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class AccessDeniedExceptionHandler extends AbstractExceptionHandler<AccessDeniedException> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(AccessDeniedException exception) {
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message("You have no sufficient privileges to execute this operation")
				.build();

		return handleErrorModelResponse(HttpStatus.FORBIDDEN, "Access denied", Arrays.asList(detail));
	}

}
