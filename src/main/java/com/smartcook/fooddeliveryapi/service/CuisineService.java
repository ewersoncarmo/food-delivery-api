package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.domain.model.request.CuisineModelRequest;
import com.smartcook.fooddeliveryapi.persistence.CuisineRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class CuisineService {

	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Cuisine create(Cuisine cuisine) {
		validateDuplicatedName(cuisine.getName());
		
		return cuisineRepository.save(cuisine);
	}

	public List<Cuisine> findAll() {
		return cuisineRepository.findAll();
	}

	public Cuisine findById(Long id) {
		// M-1=No Cuisine with Id {0} was found.
		return cuisineRepository.findById(id).orElseThrow(() -> new ServiceException("M-1", id));
	}

	public Cuisine update(CuisineModelRequest cuisineModelRequest, Cuisine cuisine) {
		if (!cuisine.getName().equals(cuisineModelRequest.getName())) {
			validateDuplicatedName(cuisineModelRequest.getName());
		}
		
		return cuisineRepository.save(cuisine);
	}
	
	public void delete(Long id) {
		Cuisine cuisine = findById(id);
		
		cuisineRepository.delete(cuisine);
	}

	private void validateDuplicatedName(String name) {
		// M-2=Cuisine with Name {0} already exists.
		cuisineRepository.findByName(name)
			.ifPresent(c -> {
				throw new ServiceException("M-2", c.getName()); 
				});
	}
}
