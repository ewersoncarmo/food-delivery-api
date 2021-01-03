package com.smartcook.fooddeliveryapi.configuration.security.authorizationserver;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class AuthUser extends User {

	private static final long serialVersionUID = -9101851761644684439L;

	private Long userId;
	private String fullName;
	
	public AuthUser(com.smartcook.fooddeliveryapi.domain.entity.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		
		this.userId = user.getId();
		this.fullName = user.getName();
	}
}
