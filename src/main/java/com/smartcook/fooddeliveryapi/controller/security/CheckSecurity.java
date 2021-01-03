package com.smartcook.fooddeliveryapi.controller.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Cuisines {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CUISINES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {}

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
	}
	
	public @interface Restaurants {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANTS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and "
				+ "(hasAuthority('EDIT_RESTAURANTS') or @authenticationSecurityConfig.isUserResponsibleForRestaurant(#id))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanManage {}

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
	}
	
	public @interface PurchaseOrders {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanCreate {}
		
		// Performs validation before executing method
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		// Performs validation after executing method and before returning the response
		@PostAuthorize("hasAuthority('QUERY_PURCHASE_ORDERS') or "
				+ "@authenticationSecurityConfig.isAuthenticatedUserTheSame(returnObject.body.data.user.id) or "
				+ "@authenticationSecurityConfig.isUserResponsibleForRestaurant(returnObject.body.data.restaurant.id)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and "
				+ "(hasAuthority('QUERY_PURCHASE_ORDERS') or "
				+ "@authenticationSecurityConfig.isAuthenticatedUserTheSame(#filter.userId) or "
				+ "@authenticationSecurityConfig.isUserResponsibleForRestaurant(#filter.restaurantId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanSearch {}
		
		@PreAuthorize("@authenticationSecurityConfig.canManagePurchaseOrder(#purchaseOrderId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanManage {}
	}
	
	public @interface PaymentMethods {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PAYMENT_METHODS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {}

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
	}
	
	public @interface States {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_STATES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {}

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
	}
	
	public @interface Cities {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CITIES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {}

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
	}
	
	public @interface UsersGroupsPermissions {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @authenticationSecurityConfig.isAuthenticatedUserTheSame(#id)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEditPassword {}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and "
				+ "(hasAuthority('EDIT_USERS_GROUPS_PERMISSIONS') or @authenticationSecurityConfig.isAuthenticatedUserTheSame(#id))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEditData {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_USERS_GROUPS_PERMISSIONS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {}
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('QUERY_USERS_GROUPS_PERMISSIONS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
	}
	
	public @interface Statistics {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GENERATE_REPORTS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanQuery {}
	}
}
