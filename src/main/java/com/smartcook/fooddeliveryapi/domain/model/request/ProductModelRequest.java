package com.smartcook.fooddeliveryapi.domain.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductModelRequest {

	@NotBlank
	@Size(max = 80)
	private String name;
	
	@NotBlank
	@Size(max = 80)
	private String description;

	@NotNull
	private BigDecimal price;
	
	@NotNull
	private Boolean active;
	
}
