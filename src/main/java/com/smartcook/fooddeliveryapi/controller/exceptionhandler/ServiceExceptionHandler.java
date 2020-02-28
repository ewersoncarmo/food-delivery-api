package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Component
public class ServiceExceptionHandler extends AbstractExceptionHandler<ServiceException> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public ResponseEntity<ModelResponse<Object>> handleException(ServiceException exception) {
		String message = messageSource.getMessage(exception.getErrorCode(), exception.getParameters(), LocaleContextHolder.getLocale());
		
		ErrorDetailModelResponse detail = ErrorDetailModelResponse
				.builder()
					.code(exception.getErrorCode())
					.message(message)
				.build();

		return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Service", Arrays.asList(detail));
	}

}
