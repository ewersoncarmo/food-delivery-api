package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	Optional<State> findByName(String name);

	@Query("select e                   "
		 + "from   State e             "
		 + "where  e.name  = :name and "
		 + "       e.id   != :id       ")
	Optional<State> findByDuplicatedName(@Param("name") String name, @Param("id") Long id);
}
