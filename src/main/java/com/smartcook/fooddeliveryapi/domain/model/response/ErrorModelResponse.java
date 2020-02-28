package com.smartcook.fooddeliveryapi.domain.model.response;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ErrorModelResponse {

	private Integer status;
	private OffsetDateTime timestamp;
	private String title;
	private List<ErrorDetailModelResponse> details;

}
