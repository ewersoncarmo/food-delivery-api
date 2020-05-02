package com.smartcook.fooddeliveryapi.domain.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserChangePasswordModelRequest {

	@NotBlank
	@Size(max = 20)
	private String currentPassword;
	
	@NotBlank
	@Size(max = 20)
	private String newPassword;
	
}
