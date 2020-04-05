package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

	Optional<PaymentMethod> findByDescription(String description);

	@Query("select p                                 "
		 + "from   PaymentMethod p             	     "
		 + "where  p.description  = :description and "
		 + "       p.id          != :id       	     ")
	Optional<PaymentMethod> findByDuplicatedDescription(@Param("description") String description, @Param("id") Long id);

}
