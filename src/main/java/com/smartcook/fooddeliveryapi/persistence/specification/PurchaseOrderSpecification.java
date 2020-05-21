package com.smartcook.fooddeliveryapi.persistence.specification;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.filter.PurchaseOrderFilter;

public class PurchaseOrderSpecification {

	public static Specification<PurchaseOrder> filter(PurchaseOrderFilter filter) {
		return (root, query, builder) -> {
			root.fetch("restaurant").fetch("cuisine");
			root.fetch("restaurant").fetch("address").fetch("city").fetch("state");
			root.fetch("user");
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getUserId() != null) {
				predicates.add(builder.equal(root.get("user"), filter.getUserId()));
			}
			
			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			if (filter.getCreationDateBegin() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getCreationDateBegin()));
			}
			
			if (filter.getCreationDateEnd() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getCreationDateEnd()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
