package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModelRequest {

	@NotBlank
	@Size(max = 80)
	private String name;
	
	@NotBlank
	@Size(max = 80)
	@Email
	private String email;
	
	@NotBlank
	@Size(max = 20)
	private String password;
}
