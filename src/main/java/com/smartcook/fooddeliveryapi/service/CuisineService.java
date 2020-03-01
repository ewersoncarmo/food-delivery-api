package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;
import com.smartcook.fooddeliveryapi.persistence.CuisineRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class CuisineService {

	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Cuisine create(Cuisine cuisine) {
		// M-2=Cuisine with Name {0} already exists.
		cuisineRepository.findByName(cuisine.getName())
			.ifPresent(c -> {
				throw new ServiceException("M-2", cuisine.getName()); 
				});
		
		return cuisineRepository.save(cuisine);
	}

	public List<Cuisine> findAll() {
		return cuisineRepository.findAll();
	}

	public Cuisine findById(Long id) {
		// M-1=No Cuisine with Id {0} was found.
		return cuisineRepository.findById(id).orElseThrow(() -> new ServiceException("M-1", id));
	}

	public Cuisine update(Cuisine cuisine) {
		// M-2=Cuisine with Name {0} already exists.
		cuisineRepository.findByDuplicatedName(cuisine.getName(), cuisine.getId())
			.ifPresent(c -> {
				throw new ServiceException("M-2", cuisine.getName()); 
				});
		
		return cuisineRepository.save(cuisine);
	}
	
	public void delete(Long id) {
		Cuisine cuisine = findById(id);
		
		// TODO check if cuisine has restaurants
		
		cuisineRepository.delete(cuisine);
	}

}
