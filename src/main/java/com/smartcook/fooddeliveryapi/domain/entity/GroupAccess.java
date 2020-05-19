package com.smartcook.fooddeliveryapi.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class GroupAccess {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@EqualsAndHashCode.Include
	private String name;
	
	@ManyToMany
	@JoinTable(name = "group_access_permission",
			   joinColumns = @JoinColumn(name = "group_access_id"),
			   inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<Permission> permissions = new HashSet<>();
	
	public void addPermission(Permission permission) {
		getPermissions().add(permission);
	}

	public void removePermission(Permission permission) {
		getPermissions().remove(permission);
	}
}
