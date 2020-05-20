package com.smartcook.fooddeliveryapi.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PurchaseOrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private Integer quantity;
	private String note;

	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "purchase_order_id")
	private PurchaseOrder purchaseOrder;

	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	public void calculateTotalPrice() {
	    BigDecimal unitPrice = getUnitPrice();
	    Integer quantity = getQuantity();

	    if (unitPrice == null) {
	        unitPrice = BigDecimal.ZERO;
	    }

	    if (quantity == null) {
	        quantity = 0;
	    }

	    this.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
	}
}
