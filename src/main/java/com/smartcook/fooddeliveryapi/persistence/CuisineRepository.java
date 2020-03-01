package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

	Optional<Cuisine> findByName(String name);

	@Query("select c                   "
		 + "from   Cuisine c           "
		 + "where  c.name  = :name and "
		 + "       c.id   != :id       ")
	Optional<Cuisine> findByDuplicatedName(@Param("name") String name, @Param("id") Long id);
}
