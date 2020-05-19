package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.Permission;
import com.smartcook.fooddeliveryapi.persistence.PermissionRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	public Permission create(Permission permission) {
		// M-24=Permission with Name {0} already exists.
		permissionRepository.findByName(permission.getName())
			.ifPresent(p -> {
				throw new ServiceException("M-24", permission.getName()); 
				});
		
		return permissionRepository.save(permission);
	}

	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	public Permission findById(Long id) {
		// M-23=No Permission with Id {0} was found.
		return permissionRepository.findById(id).orElseThrow(() -> new ServiceException("M-23", id));
	}

	public Permission update(Permission permission) {
		// M-24=Permission with Name {0} already exists.
		permissionRepository.findByDuplicatedName(permission.getName(), permission.getId())
			.ifPresent(s -> {
				throw new ServiceException("M-24", permission.getName()); 
				});
		
		return permissionRepository.save(permission);
	}
	
	public void delete(Long id) {
		Permission permission = findById(id);

		// TODO check if permission method has relationships
		
		permissionRepository.delete(permission);
	}

}
