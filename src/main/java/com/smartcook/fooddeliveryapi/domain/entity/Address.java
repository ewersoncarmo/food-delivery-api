package com.smartcook.fooddeliveryapi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Address {

	@Column(name = "address_zip_code")
	private String zipCode;
	
	@Column(name = "address_street")
	private String street;
	
	@Column(name = "address_number")
	private String number;
	
	@Column(name = "address_complement")
	private String complement;
	
	@Column(name = "address_neighborhood")
	private String neighborhood;
	
	@ManyToOne
	@JoinColumn(name = "address_city_id")
	private City city;
}
