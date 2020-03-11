package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.City;
import com.smartcook.fooddeliveryapi.persistence.CityRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateService stateService;
	
	public City create(City city) {
		city.setState(stateService.findById(city.getState().getId()));
		
		// M-8=City with Name {0} and State {1} already exists.
		cityRepository.findByName(city.getName(), city.getState().getId())
			.ifPresent(c -> {
				throw new ServiceException("M-8", city.getName(), city.getState().getName()); 
				});
		
		
		return cityRepository.save(city);
	}

	public List<City> findAll() {
		return cityRepository.findAll();
	}

	public City findById(Long id) {
		// M-7=No City with Id {0} was found.
		return cityRepository.findById(id).orElseThrow(() -> new ServiceException("M-7", id));
	}
	
	public List<City> findByState_Id(Long stateId) {
		return cityRepository.findByState_Id(stateId);
	}

	public City update(City city) {
		city.setState(stateService.findById(city.getState().getId()));
		
		// M-8=City with Name {0} and State {1} already exists.
		cityRepository.findByDuplicatedName(city.getName(), city.getState().getId(), city.getId())
			.ifPresent(c -> {
				throw new ServiceException("M-8", city.getName(), city.getState().getName()); 
				});
		
		return cityRepository.save(city);
	}
	
	public void delete(Long id) {
		City city = findById(id);
		
		// TODO check city relationships
		
		cityRepository.delete(city);
	}

}
