package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.State;
import com.smartcook.fooddeliveryapi.persistence.StateRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityService cityService;
	
	public State create(State state) {
		// M-5=State with Name {0} already exists.
		stateRepository.findByName(state.getName())
			.ifPresent(s -> {
				throw new ServiceException("M-5", state.getName()); 
				});
		
		return stateRepository.save(state);
	}

	public List<State> findAll() {
		return stateRepository.findAll();
	}

	public State findById(Long id) {
		// M-4=No State with Id {0} was found.
		return stateRepository.findById(id).orElseThrow(() -> new ServiceException("M-4", id));
	}

	public State update(State state) {
		// M-5=State with Name {0} already exists.
		stateRepository.findByDuplicatedName(state.getName(), state.getId())
			.ifPresent(s -> {
				throw new ServiceException("M-5", state.getName()); 
				});
		
		return stateRepository.save(state);
	}
	
	public void delete(Long id) {
		State state = findById(id);
		
		// M-6=State has cities. It can not be removed.
		if (cityService.findByState_Id(id).size() > 0) {
			throw new ServiceException("M-6");
		}
		
		stateRepository.delete(state);
	}

}
