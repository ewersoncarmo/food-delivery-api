package com.smartcook.fooddeliveryapi.domain.model.response;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductModelResponse extends RepresentationModel<ProductModelResponse> {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Boolean active;
}
