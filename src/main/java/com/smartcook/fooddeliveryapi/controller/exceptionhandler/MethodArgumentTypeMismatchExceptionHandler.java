package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class MethodArgumentTypeMismatchExceptionHandler extends AbstractExceptionHandler<MethodArgumentTypeMismatchException> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(MethodArgumentTypeMismatchException exception) {
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message(String.format("Invalid value for parameter '%s'", exception.getName()))
					.action(String.format("Inform a valid value of type %s", exception.getRequiredType().getSimpleName()))
				.build();

		return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Type mismatch", Arrays.asList(detail));
	}

}
