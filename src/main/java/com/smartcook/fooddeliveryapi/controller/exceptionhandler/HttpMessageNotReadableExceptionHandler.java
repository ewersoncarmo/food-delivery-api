package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class HttpMessageNotReadableExceptionHandler extends AbstractExceptionHandler<HttpMessageNotReadableException> {

	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(HttpMessageNotReadableException exception) {
		Throwable rootCause = exception.getRootCause();
		
		String message;
		String action;
		
		if (rootCause instanceof InvalidFormatException) {
			InvalidFormatException cause = (InvalidFormatException) rootCause;

			message = String.format("Invalid value for property '%s'", joinPath(cause.getPath()));
			action = String.format("Inform a valid value of type %s", cause.getTargetType().getSimpleName());
		} else if (rootCause instanceof PropertyBindingException ) {
			PropertyBindingException  cause = (PropertyBindingException) rootCause;

			message = String.format("Property '%s' does not exist", joinPath(cause.getPath()));
			action = "Correct or remove the property and try again";
		} else {
			message = "Invalid request body";
			action = "Check the request body and try again";
		}
		
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.message(message)
					.action(action)
				.build();

		return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Invalid request", Arrays.asList(detail));
	}
	
	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(reference -> reference.getFieldName())
			.collect(Collectors.joining("."));
	}

}
