package com.smartcook.fooddeliveryapi.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.smartcook.fooddeliveryapi.configuration.security.AuthenticationSecurityConfig;
import com.smartcook.fooddeliveryapi.controller.PurchaseOrderController;
import com.smartcook.fooddeliveryapi.controller.PurchaseOrderStatusController;
import com.smartcook.fooddeliveryapi.controller.RestaurantController;
import com.smartcook.fooddeliveryapi.controller.UserController;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.response.PurchaseOrderSummaryModelResponse;

@Component
public class PurchaseOrderSummaryAssembler extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderSummaryModelResponse> {

	public PurchaseOrderSummaryAssembler() {
		super(PurchaseOrderController.class, PurchaseOrderSummaryModelResponse.class);
	}

	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	private AuthenticationSecurityConfig authenticationSecurityConfig;
	
	@Override
	public PurchaseOrderSummaryModelResponse toModel(PurchaseOrder entity) {
		// add self relation
		PurchaseOrderSummaryModelResponse purchaseOrderSummaryModelResponse = createModelWithId(entity.getId(), entity);
		
		modelMapper.map(entity, purchaseOrderSummaryModelResponse);
		
		TemplateVariables pageVariables = new TemplateVariables(
				new TemplateVariable("page", VariableType.REQUEST_PARAM),
				new TemplateVariable("size", VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", VariableType.REQUEST_PARAM));
		
		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("userId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
				new TemplateVariable("creationDateBegin", VariableType.REQUEST_PARAM),
				new TemplateVariable("creationDateEnd", VariableType.REQUEST_PARAM));
		
		String purchaseOrdersUrl = linkTo(PurchaseOrderController.class).toUri().toString();
		// add collection relation
		purchaseOrderSummaryModelResponse.add(new Link(UriTemplate.of(purchaseOrdersUrl, pageVariables.concat(filterVariables)), "purchase-orders"));
		
		if (authenticationSecurityConfig.canManagePurchaseOrder(purchaseOrderSummaryModelResponse.getId())) {
			// add self relation to confirm
			if (entity.canBeConfirmed()) {
				purchaseOrderSummaryModelResponse.add(linkTo(methodOn(PurchaseOrderStatusController.class).confirm(purchaseOrderSummaryModelResponse.getId())).withRel("confirm"));
			}
			// add self relation to deliver
			if (entity.canBeDelivered()) {
				purchaseOrderSummaryModelResponse.add(linkTo(methodOn(PurchaseOrderStatusController.class).deliver(purchaseOrderSummaryModelResponse.getId())).withRel("deliver"));
			}
			// add self relation to cancel
			if (entity.canBeCenceled()) {
				purchaseOrderSummaryModelResponse.add(linkTo(methodOn(PurchaseOrderStatusController.class).cancel(purchaseOrderSummaryModelResponse.getId())).withRel("cancel"));
			}
		}
		
		// add self relation to restaurant
		purchaseOrderSummaryModelResponse.getRestaurant().add(linkTo(methodOn(RestaurantController.class).findById(purchaseOrderSummaryModelResponse.getRestaurant().getId())).withSelfRel());
		// add self relation to user
		purchaseOrderSummaryModelResponse.getUser().add(linkTo(methodOn(UserController.class).findById(purchaseOrderSummaryModelResponse.getUser().getId())).withSelfRel());
		
		return purchaseOrderSummaryModelResponse;
	}

}
