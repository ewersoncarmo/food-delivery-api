package com.smartcook.fooddeliveryapi.controller.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.service.exception.ReportException;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@ControllerAdvice
public class ApiExceptionHandler {

	@Autowired
	private ServiceExceptionHandler serviceExceptionHandler;
	
	@Autowired
	private HttpRequestMethodNotSupportedExceptionHandler httpRequestMethodNotSupportedExceptionHandler;
	
	@Autowired
	private HttpMediaTypeNotSupportedExceptionHandler httpMediaTypeNotSupportedExceptionHandler;
	
	@Autowired
	private MissingServletRequestParameterExceptionHandler missingServletRequestParameterExceptionHandler;
	
	@Autowired
	private MethodArgumentTypeMismatchExceptionHandler methodArgumentTypeMismatchExceptionHandler;
	
	@Autowired
	private HttpMessageNotReadableExceptionHandler httpMessageNotReadableExceptionHandler;
	
	@Autowired
	private MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler;
	
	@Autowired
	private BindExceptionHandler bindExceptionHandler;
	
	@Autowired
	private NoHandlerFoundExceptionHandler noHandlerFoundExceptionHandler;
	
	@Autowired
	private GenericExceptionHandler genericExceptionHandler;
	
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ModelResponse<Object>> handleServiceException(ServiceException exception) {
		return serviceExceptionHandler.handleException(exception);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ModelResponse<Object>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
		return httpRequestMethodNotSupportedExceptionHandler.handleException(exception);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	protected ResponseEntity<ModelResponse<Object>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception) {
		return httpMediaTypeNotSupportedExceptionHandler.handleException(exception);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ModelResponse<Object>> handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
		return missingServletRequestParameterExceptionHandler.handleException(exception);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ModelResponse<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest request) {
		return methodArgumentTypeMismatchExceptionHandler.handleException(exception);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ModelResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, WebRequest request) {
		return httpMessageNotReadableExceptionHandler.handleException(exception);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ModelResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		return methodArgumentNotValidExceptionHandler.handleException(exception);
	}
	
	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ModelResponse<Object>> handleBindException(BindException exception) {
		return bindExceptionHandler.handleException(exception);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ModelResponse<Object>> handleNoHandlerFoundException(NoHandlerFoundException exception) {
		return noHandlerFoundExceptionHandler.handleException(exception);
	}

	@ExceptionHandler({Exception.class, ReportException.class})
	public ResponseEntity<ModelResponse<Object>> handleGenericException(Exception exception) {
		return genericExceptionHandler.handleException(exception);
	}
}
