package com.smartcook.fooddeliveryapi.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.domain.entity.Product;
import com.smartcook.fooddeliveryapi.domain.entity.ProductPhoto;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.persistence.ProductRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;
import com.smartcook.fooddeliveryapi.service.storage.ProductPhotoStorageService;
import com.smartcook.fooddeliveryapi.service.storage.ProductPhotoStorageService.Photo;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductPhotoStorageService productPhotoStorageService;

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

	@Transactional
	public ProductPhoto savePhoto(ProductPhoto productPhoto, InputStream inputStream) {
		Restaurant restaurant = productPhoto.getProduct().getRestaurant();
		Product product = productPhoto.getProduct();
		
		productPhoto.setFileName(productPhotoStorageService.generateFileName(productPhoto.getFileName()));

		Optional<ProductPhoto> existingPhoto = findExistingPhoto(restaurant.getId(), product.getId());

		String existingFile = null;
		
		if (existingPhoto.isPresent()) {
			ProductPhoto existingProductPhoto = existingPhoto.get();
			existingFile = existingProductPhoto.getFileName();
			
			productRepository.delete(existingProductPhoto);
		} 
		
		productPhoto = productRepository.saveAndFlush(productPhoto);
		
		Photo photoFile = Photo
			.builder()
				.fileName(productPhoto.getFileName())
				.inputStream(inputStream)
			.build();
		
		productPhotoStorageService.store(existingFile, photoFile);
		
		return productPhoto;
	}

	public ProductPhoto findPhoto(Long restaurantId, Long productId) {
		findByRestaurant(restaurantId, productId);
		
		// M-30=No photo for Product with Id {0} was found.
		return findExistingPhoto(restaurantId, productId).orElseThrow(() -> new ServiceException("M-30", productId));
	}

	@Transactional
	public void removePhoto(ProductPhoto photo) {
		productRepository.delete(photo);
		productPhotoStorageService.remove(photo.getFileName());
	}
	
	public InputStream retrieve(String fileName) {
		return productPhotoStorageService.retrieve(fileName);
	}

	private Optional<ProductPhoto> findExistingPhoto(Long restaurantId, Long productId) {
		return productRepository.findPhotoById(restaurantId, productId);
	}

}
