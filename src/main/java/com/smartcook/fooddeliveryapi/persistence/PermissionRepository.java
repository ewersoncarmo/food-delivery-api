package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Optional<Permission> findByName(String name);

	@Query("select p                   "
		 + "from   Permission p        "
		 + "where  p.name  = :name and "
		 + "       p.id   != :id       ")
	Optional<Permission> findByDuplicatedName(@Param("name") String name, @Param("id") Long id);
}
