package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.configuration.security.AuthenticationSecurityConfig;
import com.smartcook.fooddeliveryapi.controller.CityController;
import com.smartcook.fooddeliveryapi.controller.PaymentMethodController;
import com.smartcook.fooddeliveryapi.controller.PurchaseOrderController;
import com.smartcook.fooddeliveryapi.controller.PurchaseOrderStatusController;
import com.smartcook.fooddeliveryapi.controller.RestaurantController;
import com.smartcook.fooddeliveryapi.controller.RestaurantProductController;
import com.smartcook.fooddeliveryapi.controller.StateController;
import com.smartcook.fooddeliveryapi.controller.UserController;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.request.PurchaseOrderModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.PurchaseOrderModelResponse;

@Component
public class PurchaseOrderAssembler extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderModelResponse> {

	public PurchaseOrderAssembler() {
		super(PurchaseOrderController.class, PurchaseOrderModelResponse.class);
	}

	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	private AuthenticationSecurityConfig authenticationSecurityConfig;
	
	public PurchaseOrder toEntity(PurchaseOrderModelRequest request) {
		return modelMapper.map(request, PurchaseOrder.class);
	}

	@Override
	public PurchaseOrderModelResponse toModel(PurchaseOrder entity) {
		// add self relation
		PurchaseOrderModelResponse purchaseOrderModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, purchaseOrderModelResponse);
		
		// add collection relation
		purchaseOrderModelResponse.add(linkTo(PurchaseOrderController.class).withRel("purchase-orders"));
		
		if (authenticationSecurityConfig.canManagePurchaseOrder(purchaseOrderModelResponse.getId())) {
			// add self relation to confirm
			if (entity.canBeConfirmed()) {
				purchaseOrderModelResponse.add(linkTo(methodOn(PurchaseOrderStatusController.class).confirm(purchaseOrderModelResponse.getId())).withRel("confirm"));
			}
			// add self relation to deliver
			if (entity.canBeDelivered()) {
				purchaseOrderModelResponse.add(linkTo(methodOn(PurchaseOrderStatusController.class).deliver(purchaseOrderModelResponse.getId())).withRel("deliver"));
			}
			// add self relation to cancel
			if (entity.canBeCenceled()) {
				purchaseOrderModelResponse.add(linkTo(methodOn(PurchaseOrderStatusController.class).cancel(purchaseOrderModelResponse.getId())).withRel("cancel"));
			}
		}
		
		// add self relation to restaurant
		purchaseOrderModelResponse.getRestaurant().add(linkTo(methodOn(RestaurantController.class).findById(purchaseOrderModelResponse.getRestaurant().getId())).withSelfRel());
		// add self relation to user
		purchaseOrderModelResponse.getUser().add(linkTo(methodOn(UserController.class).findById(purchaseOrderModelResponse.getUser().getId())).withSelfRel());
		// add self relation to payment method
		purchaseOrderModelResponse.getPaymentMethod().add(linkTo(methodOn(PaymentMethodController.class).findById(purchaseOrderModelResponse.getPaymentMethod().getId())).withSelfRel());
		// add self relation to city
		purchaseOrderModelResponse.getAddress().getCity().add(linkTo(methodOn(CityController.class).findById(purchaseOrderModelResponse.getAddress().getCity().getId())).withSelfRel());
		// add self relation to state
		purchaseOrderModelResponse.getAddress().getCity().getState().add(linkTo(methodOn(StateController.class).findById(purchaseOrderModelResponse.getAddress().getCity().getState().getId())).withSelfRel());
		// add self relation to products
		purchaseOrderModelResponse.getItems().forEach(item -> {
			item.add(linkTo(methodOn(RestaurantProductController.class).findById(purchaseOrderModelResponse.getRestaurant().getId(), item.getProductId())).withRel("product"));
		});
		
		return purchaseOrderModelResponse;
	}

}
