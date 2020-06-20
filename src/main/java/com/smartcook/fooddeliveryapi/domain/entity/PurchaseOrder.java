package com.smartcook.fooddeliveryapi.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class PurchaseOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal subTotal;
	private BigDecimal freightRate;
	private BigDecimal amount;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus = OrderStatus.CREATED;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_method_id")
	private PaymentMethod paymentMethod;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.PERSIST)
	private List<PurchaseOrderItem> items = new ArrayList<>();
	
	@CreationTimestamp
	private OffsetDateTime creationDate;

	private OffsetDateTime confirmationDate;
	private OffsetDateTime deliveryDate;
	private OffsetDateTime cancellationDate;
	
	public void calculateAmount() {
		getItems().forEach(PurchaseOrderItem::calculateTotalPrice);
		
	    this.subTotal = getItems()
	    		.stream()
	    		.map(item -> item.getTotalPrice())
	    			.reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.amount = this.subTotal.add(this.freightRate);
	}

	public void confirm() {
		this.orderStatus = OrderStatus.CONFIRMED;
		this.confirmationDate = OffsetDateTime.now();
	}

	public void deliver() {
		this.orderStatus = OrderStatus.DELIVERED;
		this.deliveryDate = OffsetDateTime.now();
	}

	public void cancel() {
		this.orderStatus = OrderStatus.CANCELED;
		this.cancellationDate = OffsetDateTime.now();
	}
	
}
