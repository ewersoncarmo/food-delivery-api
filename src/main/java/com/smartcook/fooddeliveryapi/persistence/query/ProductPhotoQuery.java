package com.smartcook.fooddeliveryapi.persistence.query;

import com.smartcook.fooddeliveryapi.domain.entity.ProductPhoto;

public interface ProductPhotoQuery {

	ProductPhoto save(ProductPhoto productPhoto);
	
	ProductPhoto saveAndFlush(ProductPhoto productPhoto);

	void delete(ProductPhoto productPhoto);
}
