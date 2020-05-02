package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;
import com.smartcook.fooddeliveryapi.persistence.GroupAccessRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class GroupAccessService {

	@Autowired
	private GroupAccessRepository groupAccessRepository;
	
	public GroupAccess create(GroupAccess groupAccess) {
		// M-15=Group Access with Name {0} already exists.
		groupAccessRepository.findByName(groupAccess.getName())
			.ifPresent(g -> {
				throw new ServiceException("M-15", groupAccess.getName()); 
				});
		
		return groupAccessRepository.save(groupAccess);
	}

	public List<GroupAccess> findAll() {
		return groupAccessRepository.findAll();
	}

	public GroupAccess findById(Long id) {
		// M-14=No Group Access with Id {0} was found.
		return groupAccessRepository.findById(id).orElseThrow(() -> new ServiceException("M-14", id));
	}

	public GroupAccess update(GroupAccess groupAccess) {
		// M-15=Group Access with Name {0} already exists.
		groupAccessRepository.findByDuplicatedName(groupAccess.getName(), groupAccess.getId())
			.ifPresent(g -> {
				throw new ServiceException("M-15", groupAccess.getName()); 
				});
		
		return groupAccessRepository.save(groupAccess);
	}
	
	public void delete(Long id) {
		GroupAccess groupAccess = findById(id);
		
		// TODO 
		// M-16=Group Access has users. It can not be removed.
//		if (restaurantService.findByCuisine_Id(id).size() > 0) {
//			throw new ServiceException("M-3");
//		}
		
		groupAccessRepository.delete(groupAccess);
	}

}
