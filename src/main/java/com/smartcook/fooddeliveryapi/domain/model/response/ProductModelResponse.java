package com.smartcook.fooddeliveryapi.domain.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductModelResponse {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Boolean active;
}
