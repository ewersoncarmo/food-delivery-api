package com.smartcook.fooddeliveryapi.domain.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModelResponse {

	private Long id;
	private String name;
	private String email;
}