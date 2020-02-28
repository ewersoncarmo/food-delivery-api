package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.Cuisine;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

	Optional<Cuisine> findByName(String name);
}
