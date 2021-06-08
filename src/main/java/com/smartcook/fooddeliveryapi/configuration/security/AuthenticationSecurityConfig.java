package com.smartcook.fooddeliveryapi.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.persistence.PurchaseOrderRepository;
import com.smartcook.fooddeliveryapi.persistence.RestaurantRepository;

@Component
public class AuthenticationSecurityConfig {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	public boolean isUserResponsibleForRestaurant(Long restaurantId) {
		if (restaurantId == null) {
			return false;
		}
		
		return restaurantRepository.findByResponsibleUser(restaurantId, getUserId())
				.isPresent();
	}
	
	public boolean canManagePurchaseOrder(Long purchaseOrderId) {
		return hasAuthority("SCOPE_WRITE") && (hasAuthority("MANAGE_PURCHASE_ORDERS") ||
				isUserResponsibleForPurchaseOrder(purchaseOrderId));
	}
	
	public boolean isAuthenticatedUserTheSame(Long userId) {
		return getUserId() != null && userId != null && getUserId().equals(userId);
	}
	
	public Long getUserId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("user_id");
	}
	
	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	private boolean isUserResponsibleForPurchaseOrder(Long purchaseOrderId) {
		return purchaseOrderRepository.findByResponsibleUser(purchaseOrderId, getUserId())
				.isPresent();
	}
	
	private boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals(authorityName));
	}
}
