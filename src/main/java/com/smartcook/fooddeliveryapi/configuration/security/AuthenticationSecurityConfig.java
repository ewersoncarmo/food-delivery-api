package com.smartcook.fooddeliveryapi.configuration.security;

import com.smartcook.fooddeliveryapi.domain.entity.User;
import com.smartcook.fooddeliveryapi.persistence.PurchaseOrderRepository;
import com.smartcook.fooddeliveryapi.persistence.RestaurantRepository;
import com.smartcook.fooddeliveryapi.persistence.UserRepository;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class AuthenticationSecurityConfig {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private UserRepository userRepository;
	
	public boolean isUserResponsibleForRestaurant(Long restaurantId) {
		if (restaurantId == null) {
			return false;
		}
		
		return restaurantRepository.findByResponsibleUser(restaurantId, getUserId())
				.isPresent();
	}
	
	public boolean canManagePurchaseOrder(Long purchaseOrderId) {
		return hasAuthority("WRITE") && (hasAuthority("MANAGE_PURCHASE_ORDERS") ||
				isUserResponsibleForPurchaseOrder(purchaseOrderId));
	}
	
	public boolean isAuthenticatedUserTheSame(Long userId) {
		return getUserId() != null && userId != null && getUserId().equals(userId);
	}
	
	public Long getUserId() {
		Map<String, Object> attributes = getPrincipalAttributes();

		String email = (String) attributes.get("user_name");

		User user = userRepository.findByEmail(email).get();

		return user.getId();
	}

	private Map<String, Object> getPrincipalAttributes() {
		DefaultOAuth2AuthenticatedPrincipal principal = (DefaultOAuth2AuthenticatedPrincipal) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return principal.getAttributes();
	}
	
	private boolean isUserResponsibleForPurchaseOrder(Long purchaseOrderId) {
		return purchaseOrderRepository.findByResponsibleUser(purchaseOrderId, getUserId())
				.isPresent();
	}

	public boolean hasAuthority(String authority) {
		Map<String, Object> attributes = getPrincipalAttributes();

		return attributes.entrySet().stream()
				.filter(a -> a.getKey().equals("authorities") || a.getKey().equals("scope"))
				.anyMatch(a -> {
					JSONArray value = (JSONArray) a.getValue();
					return value.stream().anyMatch(v -> v.equals(authority));
				});
	}
}
