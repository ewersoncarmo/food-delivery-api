package com.smartcook.fooddeliveryapi.domain.entity;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@EqualsAndHashCode.Include
	private String email;
	
	private String password;
	
	@CreationTimestamp
	private OffsetDateTime creationDate;
	
	@ManyToMany
	@JoinTable(name = "user_group_access",
			   joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name = "group_access_id"))
	private Set<GroupAccess> groups = new HashSet<>();
	
	public void addGroupAccess(GroupAccess groupAccess) {
		getGroups().add(groupAccess);
	}

	public void removeGroupAccess(GroupAccess groupAccess) {
		getGroups().remove(groupAccess);
	}
}
