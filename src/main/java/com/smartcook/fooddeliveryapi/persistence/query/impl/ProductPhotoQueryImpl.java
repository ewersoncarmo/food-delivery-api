package com.smartcook.fooddeliveryapi.persistence.query.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.domain.entity.ProductPhoto;
import com.smartcook.fooddeliveryapi.persistence.query.ProductPhotoQuery;

@Repository
public class ProductPhotoQueryImpl implements ProductPhotoQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public ProductPhoto save(ProductPhoto productPhoto) {
		return entityManager.merge(productPhoto);
	}

	@Transactional
	@Override
	public void delete(ProductPhoto productPhoto) {
		entityManager.remove(productPhoto);
	}
	
}
