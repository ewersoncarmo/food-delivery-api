package com.smartcook.fooddeliveryapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@GetMapping
	public ResponseEntity<ModelResponse<RootEntryPointModelResponse>> root() {
		
		RootEntryPointModelResponse rootEntryPointModelResponse = new RootEntryPointModelResponse();
		rootEntryPointModelResponse.add(linkTo(CuisineController.class).withRel("cuisines"));
		rootEntryPointModelResponse.add(linkTo(StateController.class).withRel("states"));
		rootEntryPointModelResponse.add(linkTo(CityController.class).withRel("cities"));
		rootEntryPointModelResponse.add(linkTo(PermissionController.class).withRel("permissions"));
		rootEntryPointModelResponse.add(linkTo(GroupAccessController.class).withRel("groups-access"));
		rootEntryPointModelResponse.add(linkTo(UserController.class).withRel("users"));
		rootEntryPointModelResponse.add(linkTo(PaymentMethodController.class).withRel("payment-methods"));
		rootEntryPointModelResponse.add(linkTo(RestaurantController.class).withRel("restaurants"));
		rootEntryPointModelResponse.add(linkTo(PurchaseOrderController.class).withRel("purchase-orders"));
		rootEntryPointModelResponse.add(linkTo(DailySaleController.class).withRel("statistics"));
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(rootEntryPointModelResponse));
	}
	
	private static class RootEntryPointModelResponse extends RepresentationModel<RootEntryPointModelResponse> {}
	
}
