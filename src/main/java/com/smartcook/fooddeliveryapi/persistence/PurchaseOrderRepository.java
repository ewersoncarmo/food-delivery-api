package com.smartcook.fooddeliveryapi.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {

	// This method is an example. It is not being used
	@Query("select p                    "
		 + "from   PurchaseOrder p      "
		 + "join fetch p.user           "
		 + "join fetch p.restaurant r   "
		 + "join fetch r.cuisine        "
		 + "join fetch r.address.city c "
		 + "join fetch c.state          ")
	@Override
	List<PurchaseOrder> findAll();
}
