package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcook.fooddeliveryapi.domain.entity.GroupAccess;

public interface GroupAccessRepository extends JpaRepository<GroupAccess, Long> {

	Optional<GroupAccess> findByName(String name);
	
	@Query("select g                   "
		 + "from   GroupAccess g       "
		 + "where  g.name  = :name and "
		 + "       g.id   != :id       ")
	Optional<GroupAccess> findByDuplicatedName(@Param("name") String name, @Param("id") Long id);
}
