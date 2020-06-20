package com.smartcook.fooddeliveryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.domain.entity.Product;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.filter.PurchaseOrderFilter;
import com.smartcook.fooddeliveryapi.persistence.PurchaseOrderRepository;
import com.smartcook.fooddeliveryapi.persistence.specification.PurchaseOrderSpecification;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private CityService cityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PurchaseOrderStatusService purchaseOrderStatusService;
	
	@Transactional
	public PurchaseOrder create(PurchaseOrder purchaseOrder) {
		validatePurchaseOrder(purchaseOrder);
		validatePurchaseOrderItems(purchaseOrder);
		
		purchaseOrder.setFreightRate(purchaseOrder.getRestaurant().getFreightRate());
		purchaseOrder.calculateAmount();
		
		purchaseOrderRepository.save(purchaseOrder);
		
		purchaseOrderStatusService.publichEmailEvent(purchaseOrder);
		
		return purchaseOrder;
	}

	public Page<PurchaseOrder> search(PurchaseOrderFilter filter, Pageable pageable) {
		return purchaseOrderRepository.findAll(PurchaseOrderSpecification.filter(filter), pageable);
	}

	public PurchaseOrder findById(Long id) {
		// M-25=No Purchase Order with Id {0} was found.
		return purchaseOrderRepository.findById(id).orElseThrow(() -> new ServiceException("M-25", id));
	}
	
	private void validatePurchaseOrder(PurchaseOrder purchaseOrder) {
		purchaseOrder.setRestaurant(restaurantService.findById(purchaseOrder.getRestaurant().getId()));
		purchaseOrder.setPaymentMethod(paymentMethodService.findById(purchaseOrder.getPaymentMethod().getId()));
		purchaseOrder.getAddress().setCity(cityService.findById(purchaseOrder.getAddress().getCity().getId()));
		purchaseOrder.setUser(userService.findById(1L));
		
		// M-26=Restaurant does not accept Payment Method {0}.
		if (purchaseOrder.getRestaurant().notAcceptsPaymentMethod(purchaseOrder.getPaymentMethod())) {
			throw new ServiceException("M-26", purchaseOrder.getPaymentMethod().getDescription());
		}
	}

	private void validatePurchaseOrderItems(PurchaseOrder purchaseOrder) {
		purchaseOrder.getItems().forEach(item -> {
			Product product = productService.findByRestaurant(purchaseOrder.getRestaurant().getId(), item.getProduct().getId());
			
			item.setPurchaseOrder(purchaseOrder);
			item.setProduct(product);
			item.setUnitPrice(product.getPrice());
		});
	}
}
