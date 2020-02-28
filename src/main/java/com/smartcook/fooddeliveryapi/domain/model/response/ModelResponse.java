package com.smartcook.fooddeliveryapi.domain.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class ModelResponse<T> {

	private T data;
	private ErrorModelResponse error;

	private ModelResponse() {}
	
	public static <T> ModelResponse<T> withData(T data) {
		ModelResponse<T> modelResponse = new ModelResponse<>();
		modelResponse.setData(data);

		return modelResponse;
	}
	
	public static ModelResponse<Object> withError(ErrorModelResponse error) {
		ModelResponse<Object> modelResponse = new ModelResponse<>();
		modelResponse.setError(error);

		return modelResponse;
	}
}
