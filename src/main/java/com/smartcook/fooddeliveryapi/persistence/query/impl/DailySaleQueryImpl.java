package com.smartcook.fooddeliveryapi.persistence.query.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.OrderStatus;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.dto.DailySale;
import com.smartcook.fooddeliveryapi.domain.model.filter.DailySaleFilter;
import com.smartcook.fooddeliveryapi.persistence.query.DailySaleQuery;

@Repository
public class DailySaleQueryImpl implements DailySaleQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<DailySale> searchDailySales(DailySaleFilter filter) {
		var builder = entityManager.getCriteriaBuilder();
		var query = builder.createQuery(DailySale.class);
		var root = query.from(PurchaseOrder.class);
		
		var functionDateCreationDate = builder.function("date", Date.class, root.get("creationDate"));
		
		var selection = builder.construct(DailySale.class, 
				functionDateCreationDate, // date 
				builder.count(root.get("id")), // sales
				builder.sum(root.get("amount")) // total
				);
		
		var predicates = new ArrayList<Predicate>();
		
		predicates.add(root.get("orderStatus").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

		if (filter.getRestaurantId() != null) {
		    predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
		}
		    
		if (filter.getCreationDateBegin() != null) {
		    predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"),filter.getCreationDateBegin()));
		}

		if (filter.getCreationDateEnd() != null) {
		    predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"),filter.getCreationDateEnd()));
		}
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateCreationDate);
		
		return entityManager.createQuery(query).getResultList();
	}

}
