package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;
import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.persistence.UserRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupAccessService groupAccessService;
	
	public User create(User user) {
		// M-18=User with e-mail {0} already exists.
		userRepository.findByEmail(user.getEmail())
			.ifPresent(u -> {
				throw new ServiceException("M-18", user.getEmail()); 
				});
		
		return userRepository.save(user);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		// M-17=No User with Id {0} was found.
		return userRepository.findById(id).orElseThrow(() -> new ServiceException("M-17", id));
	}

	public User update(User user) {
		// M-18=User with e-mail {0} already exists.
		userRepository.findByDuplicatedEmail(user.getEmail(), user.getId())
			.ifPresent(u -> {
				throw new ServiceException("M-18", user.getEmail()); 
				});
		
		return userRepository.save(user);
	}
	
	public void changePassword(Long id, String currentPassword, String newPassword) {
		User user = findById(id);
		
		// M-20=Wrong password for User with Id {0}.
		if (!user.getPassword().equals(currentPassword)) {
			throw new ServiceException("M-20", user.getId());
		}
		
		user.setPassword(newPassword);
		
		userRepository.save(user);
	}
	
	@Transactional
	public void addGroupAccess(Long userId, Long groupAccessId) {
		User user = findById(userId);
		GroupAccess groupAccess = groupAccessService.findById(groupAccessId);
		
		user.addGroupAccess(groupAccess);
	}
	
	@Transactional
	public void removeGroupAccess(Long userId, Long groupAccessId) {
		User user = findById(userId);
		GroupAccess groupAccess = groupAccessService.findById(groupAccessId);
		
		user.removeGroupAccess(groupAccess);
	}
	
	public void delete(Long id) {
		User user = findById(id);
		
		// TODO
		// M-19=User has purchase orders. It can not be removed.
//		if (restaurantService.findByCuisine_Id(id).size() > 0) {
//			throw new ServiceException("M-3");
//		}
		
		userRepository.delete(user);
	}

}
