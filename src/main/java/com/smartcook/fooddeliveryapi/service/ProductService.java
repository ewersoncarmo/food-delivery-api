package com.smartcook.fooddeliveryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.Product;
import com.smartcook.fooddeliveryapi.persistence.ProductRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void create(Product product) {
		// M-21=Product with Name {0} already exists.
		productRepository.findByName(product.getRestaurant().getId(), product.getName())
			.ifPresent(p -> {
				throw new ServiceException("M-21", product.getName()); 
				});
				
		productRepository.save(product);
	}

	public void update(Product product) {
		// M-21=Product with Name {0} already exists.
		productRepository.findByDuplicatedName(product.getName(), product.getRestaurant().getId(), product.getId())
			.ifPresent(p -> {
				throw new ServiceException("M-21", p.getName()); 
				});
		
		productRepository.save(product);
	}
	
	public Product findByRestaurant(Long restaurantId, Long productId) {
		// M-22=No Product with Id {0} and restaurant {1} was found.
		return productRepository.findByRestaurant(restaurantId, productId).orElseThrow(() -> new ServiceException("M-22", productId, restaurantId));
	}

}
