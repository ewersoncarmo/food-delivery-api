package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcook.fooddeliveryapi.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
 	
	@Query("select u                     "
		 + "from   User u                "
		 + "where  u.email  = :email and "
		 + "       u.id    != :id        ")
	Optional<User> findByDuplicatedEmail(@Param("email") String email, @Param("id") Long id);
}
