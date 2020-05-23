package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;
import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;
import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.domain.model.filter.RestaurantFilter;
import com.smartcook.fooddeliveryapi.persistence.RestaurantRepository;
import com.smartcook.fooddeliveryapi.persistence.specification.RestaurantSpecification;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineService cuisineService;
	
	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CityService cityService;
	
	public Restaurant create(Restaurant restaurant) {
		restaurant.setCuisine(cuisineService.findById(restaurant.getCuisine().getId()));
		restaurant.getAddress().setCity(cityService.findById(restaurant.getAddress().getCity().getId()));
		
		// M-11=Restaurant with Name {0} and City {1} already exists.
		restaurantRepository.findByName(restaurant.getName(), restaurant.getAddress().getCity().getId())
			.ifPresent(r -> {
				throw new ServiceException("M-11", restaurant.getName(), restaurant.getAddress().getCity().getName()); 
				});
		
		return restaurantRepository.save(restaurant);
	}

	public Page<Restaurant> search(RestaurantFilter filter, Pageable pageable) {
		return restaurantRepository.findAll(RestaurantSpecification.filter(filter), pageable);
	}

	public Restaurant findById(Long id) {
		// M-10=No Restaurant with Id {0} was found.
		return restaurantRepository.findById(id).orElseThrow(() -> new ServiceException("M-10", id));
	}
	
	public List<Restaurant> findByCuisine_Id(Long cuisineId) {
		return restaurantRepository.findByCuisine_Id(cuisineId);
	}

	public List<Restaurant> findByCity_Id(Long cityId) {
		return restaurantRepository.findByCity_Id(cityId);
	}
	
	public Restaurant update(Restaurant restaurant) {
		restaurant.setCuisine(cuisineService.findById(restaurant.getCuisine().getId()));
		restaurant.getAddress().setCity(cityService.findById(restaurant.getAddress().getCity().getId()));
		
		// M-11=Restaurant with Name {0} and City {1} already exists.
		restaurantRepository.findByDuplicatedName(restaurant.getName(), restaurant.getAddress().getCity().getId(), restaurant.getId())
			.ifPresent(r -> {
				throw new ServiceException("M-11", restaurant.getName(), restaurant.getAddress().getCity().getName()); 
				});
		
		return restaurantRepository.save(restaurant);
	}
	
	@Transactional
	public void activate(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.activate();
	}
	
	@Transactional
	public void deactivate(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.deactivate();
	}
	
	@Transactional
	public void activate(List<Long> ids) {
		ids.forEach(this::activate);
	}
	
	@Transactional
	public void deactivate(List<Long> ids) {
		ids.forEach(this::deactivate);
	}

	@Transactional
	public void opening(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.opening();
	}
	
	@Transactional
	public void closing(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.closing();
	}
	
	@Transactional
	public void addPaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findById(restaurantId);
		PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
		
		restaurant.addPaymentMethod(paymentMethod);
	}
	
	@Transactional
	public void removePaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findById(restaurantId);
		PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
		
		restaurant.removePaymentMethod(paymentMethod);
	}
	
	@Transactional
	public void addResponsibleUser(Long restaurantId, Long userId) {
		Restaurant restaurant = findById(restaurantId);
		User user = userService.findById(userId);
		
		restaurant.addResponsibleUser(user);
	}
	
	@Transactional
	public void removeResponsibleUser(Long restaurantId, Long userId) {
		Restaurant restaurant = findById(restaurantId);
		User user = userService.findById(userId);
		
		restaurant.removeResponsibleUser(user);
	}
	
	public void delete(Long id) {
		Restaurant restaurant = findById(id);
		
		// TODO check if restaurant has relationships
		
		restaurantRepository.delete(restaurant);
	}

}
