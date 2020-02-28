package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class HttpMediaTypeNotSupportedExceptionHandler extends AbstractExceptionHandler<HttpMediaTypeNotSupportedException> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(HttpMediaTypeNotSupportedException exception) {
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message(exception.getMessage())
					.action("Only 'application/json' media type is supported")
				.build();

		return handleErrorModelResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Media type not allowed", Arrays.asList(detail));
	}

}
