package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.persistence.RestaurantRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineService cuisineService;
	
	@Autowired
	private CityService cityService;
	
	public Restaurant create(Restaurant restaurant) {
		restaurant.setCuisine(cuisineService.findById(restaurant.getCuisine().getId()));
		restaurant.getAddress().setCity(cityService.findById(restaurant.getAddress().getCity().getId()));
		
		// M-9=Restaurant with Name {0} and City {1} already exists.
		restaurantRepository.findByName(restaurant.getName(), restaurant.getAddress().getCity().getId())
			.ifPresent(r -> {
				throw new ServiceException("M-9", restaurant.getName(), restaurant.getAddress().getCity().getName()); 
				});
		
		return restaurantRepository.save(restaurant);
	}

	public List<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}

	public Restaurant findById(Long id) {
		// M-8=No Restaurant with Id {0} was found.
		return restaurantRepository.findById(id).orElseThrow(() -> new ServiceException("M-8", id));
	}

	public Restaurant update(Restaurant restaurant) {
		restaurant.setCuisine(cuisineService.findById(restaurant.getCuisine().getId()));
		restaurant.getAddress().setCity(cityService.findById(restaurant.getAddress().getCity().getId()));
		
		// M-9=Restaurant with Name {0} and City {1} already exists.
		restaurantRepository.findByDuplicatedName(restaurant.getName(), restaurant.getAddress().getCity().getId(), restaurant.getId())
			.ifPresent(r -> {
				throw new ServiceException("M-9", restaurant.getName(), restaurant.getAddress().getCity().getName()); 
				});
		
		return restaurantRepository.save(restaurant);
	}
	
	public void delete(Long id) {
		Restaurant restaurant = findById(id);
		
		// TODO check if restaurant has relationships
		
		restaurantRepository.delete(restaurant);
	}

}
