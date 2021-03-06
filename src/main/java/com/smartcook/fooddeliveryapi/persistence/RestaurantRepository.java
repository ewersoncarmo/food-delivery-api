package com.smartcook.fooddeliveryapi.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

	@Query("select r                             "
		 + "from   Restaurant r                  "
		 + "where  r.name            = :name and "
		 + "       r.address.city.id = :cityId   ")
	Optional<Restaurant> findByName(@Param("name") String name, @Param("cityId") Long cityId);
	
	@Query("select r                                "
		 + "from   Restaurant r                     "
		 + "where  r.name             = :name   and "
		 + "       r.address.city.id  = :cityId and "
		 + "       r.id              != :id         ")
	Optional<Restaurant> findByDuplicatedName(@Param("name") String name, @Param("cityId") Long cityId, @Param("id") Long id);
	
	List<Restaurant> findByCuisine_Id(Long cuisineId);

	@Query("select r                           "
		 + "from   Restaurant r                "
		 + "where  r.address.city.id = :cityId ")
	List<Restaurant> findByCity_Id(@Param("cityId") Long cityId);
	
	@Query("select rest                           "
		 + "from   Restaurant rest                "	
		 + "join fetch rest.responsibleUsers resp "
		 + "where  rest.id = :restaurantId and    "
		 + "       resp.id = :userId              ")
	Optional<Restaurant> findByResponsibleUser(@Param("restaurantId") Long restaurantId, @Param("userId") Long usesrId);
}
