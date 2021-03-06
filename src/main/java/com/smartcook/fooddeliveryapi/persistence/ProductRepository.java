package com.smartcook.fooddeliveryapi.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.Product;
import com.smartcook.fooddeliveryapi.domain.entity.ProductPhoto;
import com.smartcook.fooddeliveryapi.persistence.query.ProductPhotoQuery;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductPhotoQuery {

	@Query("select p                                   "
		 + "from   Product p                           "
		 + "where  p.restaurant.id = :restaurantId and "
		 + "       p.name          = :name             ")
	Optional<Product> findByName(@Param("restaurantId") Long restaurantId, @Param("name") String name);

	@Query("select p                                   "
		 + "from   Product p                           "
		 + "where  p.restaurant.id = :restaurantId and "
		 + "       p.id            = :productId        ")
    Optional<Product> findByRestaurant(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

	@Query("select p                                    "
		 + "from   Product p                            "
		 + "where  p.name           = :name         and "
		 + "       p.restaurant.id  = :restaurantId and "
		 + "       p.id            != :id               ")
	Optional<Product> findByDuplicatedName(@Param("name") String name, @Param("restaurantId") Long restaurantId, @Param("id") Long id);

	@Query("select ph                                  "
		 + "from   ProductPhoto ph                     "
		 + "join   ph.product p                        "
		 + "where  p.restaurant.id = :restaurantId and "
		 + "       ph.product.id   = :productId        ")
	Optional<ProductPhoto> findPhotoById(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

}
