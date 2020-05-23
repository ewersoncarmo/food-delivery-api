package com.smartcook.fooddeliveryapi.persistence.specification;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.model.filter.RestaurantFilter;

public class RestaurantSpecification {

	public static Specification<Restaurant> filter(RestaurantFilter filter) {
		return (root, query, builder) -> {
			if (Restaurant.class.equals(query.getResultType())) {
				root.fetch("cuisine");
				root.fetch("address").fetch("city").fetch("state");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getCuisineId() != null) {
				predicates.add(builder.equal(root.get("cuisine"), filter.getCuisineId()));
			}
			
			if (filter.getCityId() != null) {
				predicates.add(builder.equal(root.get("address").get("city"), filter.getCityId()));
			}
			
			if (filter.getActive() != null) {
				predicates.add(builder.equal(root.get("active"), filter.getActive()));
			}
			
			if (filter.getOpen() != null) {
				predicates.add(builder.equal(root.get("open"), filter.getOpen()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
