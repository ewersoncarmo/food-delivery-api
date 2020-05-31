package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class BindExceptionHandler extends AbstractExceptionHandler<BindException> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<ModelResponse<Object>> handleException(BindException exception) {
		List<ErrorDetailModelResponse> details = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> {
					return ErrorDetailModelResponse
						.builder()
							.field(fieldError.getField())
							.message(String.format("%s", messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())))
						.build();
				})
				.collect(Collectors.toList());
		
		 return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Invalid data", details);
	}

}
