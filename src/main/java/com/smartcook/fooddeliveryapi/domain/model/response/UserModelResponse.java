package com.smartcook.fooddeliveryapi.domain.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModelResponse extends RepresentationModel<UserModelResponse> {

	private Long id;
	private String name;
	private String email;
}
