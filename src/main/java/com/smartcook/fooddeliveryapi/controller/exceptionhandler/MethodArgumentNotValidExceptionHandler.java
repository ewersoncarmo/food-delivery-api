package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.smartcook.fooddeliveryapi.domain.model.response.ErrorDetailModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@Component
public class MethodArgumentNotValidExceptionHandler extends AbstractExceptionHandler<MethodArgumentNotValidException> {

	@Override
	public ResponseEntity<ModelResponse<Object>> handleException(MethodArgumentNotValidException exception) {
		 List<ErrorDetailModelResponse> details = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> {
					return ErrorDetailModelResponse
						.builder()
							.field(fieldError.getField())
							.message(String.format("Field %s %s", fieldError.getField(), fieldError.getDefaultMessage()))
						.build();
				})
				.collect(Collectors.toList());
		
		 return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Invalid data", details);
	}

}
