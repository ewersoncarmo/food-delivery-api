package com.smartcook.fooddeliveryapi.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private String name;
	
	private BigDecimal freightRate;
	
	@ManyToOne
	@JoinColumn(name = "cuisine_id")
	private Cuisine cuisine;
	
	@Column
	private Boolean active = Boolean.TRUE;
	
	@Embedded
	private Address address;
	
	@CreationTimestamp
	private OffsetDateTime creationDate;
	
	@UpdateTimestamp
	private OffsetDateTime updateDate;
	
	@ManyToMany
	@JoinTable(name = "restaurant_payment_method",
			   joinColumns = @JoinColumn(name = "restaurant_id"),
			   inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private List<PaymentMethod> paymentMethods = new ArrayList<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

	public void activate() {
		setActive(true);
	}
	
	public void deactivate() {
		setActive(false);
	}
}
