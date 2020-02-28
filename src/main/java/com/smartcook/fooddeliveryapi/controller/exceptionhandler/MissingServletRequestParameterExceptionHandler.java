package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class MissingServletRequestParameterExceptionHandler extends AbstractExceptionHandler<MissingServletRequestParameterException> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(MissingServletRequestParameterException exception) {
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message(exception.getMessage())
				.build();

		return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Missing parameter", Arrays.asList(detail));
	}

}
