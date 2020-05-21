package com.smartcook.fooddeliveryapi.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private Long id;
	
	@EqualsAndHashCode.Include
	private String name;
	
	private BigDecimal freightRate;
	
	@ManyToOne
	@JoinColumn(name = "cuisine_id")
	private Cuisine cuisine;
	
	@Column
	private Boolean active = Boolean.TRUE;
	
	@Column
	private Boolean open = Boolean.TRUE;
	
	@EqualsAndHashCode.Include
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
	private Set<PaymentMethod> paymentMethods = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "restaurant_responsible_user",
			   joinColumns = @JoinColumn(name = "restaurant_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> responsibleUsers = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

	public void activate() {
		setActive(true);
	}
	
	public void deactivate() {
		setActive(false);
	}
	
	public void opening() {
		setOpen(true);
	}
	
	public void closing() {
		setOpen(false);
	}

	public void addPaymentMethod(PaymentMethod paymentMethod) {
		getPaymentMethods().add(paymentMethod);
	}

	public void removePaymentMethod(PaymentMethod paymentMethod) {
		getPaymentMethods().remove(paymentMethod);
	}
	
	public void addResponsibleUser(User user) {
		getResponsibleUsers().add(user);
	}

	public void removeResponsibleUser(User user) {
		getResponsibleUsers().remove(user);
	}
	
	public boolean acceptsPaymentMethod(PaymentMethod paymentMethod) {
	    return getPaymentMethods().contains(paymentMethod);
	}

	public boolean notAcceptsPaymentMethod(PaymentMethod paymentMethod) {
	    return !acceptsPaymentMethod(paymentMethod);
	}
}
