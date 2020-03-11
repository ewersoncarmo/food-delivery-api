package com.smartcook.fooddeliveryapi.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	@Query("select c                      "
		 + "from   City c                 "
		 + "where  c.name     = :name and "
		 + "       c.state.id = :stateId  ")
	Optional<City> findByName(@Param("name") String name, @Param("stateId") Long stateId);

	@Query("select c                          "
		 + "from   City c                     "
		 + "where  c.name      = :name    and "
		 + "       c.state.id  = :stateId and "
		 + "       c.id       != :id          ")
	Optional<City> findByDuplicatedName(@Param("name") String name, @Param("stateId") Long stateId, @Param("id") Long id);
	
	List<City> findByState_Id(Long stateId);
}
