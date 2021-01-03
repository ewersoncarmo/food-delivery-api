package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartcook.fooddeliveryapi.controller.security.CheckSecurity;
import com.smartcook.fooddeliveryapi.domain.assembler.PurchaseOrderAssembler;
import com.smartcook.fooddeliveryapi.domain.assembler.PurchaseOrderSummaryAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.PurchaseOrder;
import com.smartcook.fooddeliveryapi.domain.model.filter.PurchaseOrderFilter;
import com.smartcook.fooddeliveryapi.domain.model.request.PurchaseOrderModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.PurchaseOrderModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.PurchaseOrderSummaryModelResponse;
import com.smartcook.fooddeliveryapi.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private PurchaseOrderAssembler purchaseOrderAssembler;
	
	@Autowired
	private PurchaseOrderSummaryAssembler purchaseOrderSummaryAssembler;
	
	@Autowired
	private PagedResourcesAssembler<PurchaseOrder> pagedResourcePurchaseOrderAssembler;
	
	@CheckSecurity.PurchaseOrders.CanCreate
	@PostMapping
	public ResponseEntity<ModelResponse<PurchaseOrderSummaryModelResponse>> create(@Valid @RequestBody PurchaseOrderModelRequest purchaseOrderModelRequest) {
		PurchaseOrder purchaseOrder = purchaseOrderAssembler.toEntity(purchaseOrderModelRequest);
		
		purchaseOrderService.create(purchaseOrder);
		
		PurchaseOrderSummaryModelResponse purchaseOrderSummaryModelResponse = purchaseOrderSummaryAssembler.toModel(purchaseOrder);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(purchaseOrderSummaryModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(purchaseOrderSummaryModelResponse));
	}
	
	@CheckSecurity.PurchaseOrders.CanSearch
	@GetMapping
	public ResponseEntity<ModelResponse<PagedModel<PurchaseOrderSummaryModelResponse>>> search(PurchaseOrderFilter filter, 
			Pageable pageable) {
		Page<PurchaseOrder> purchaseOrdersPage = purchaseOrderService.search(filter, pageable);

		PagedModel<PurchaseOrderSummaryModelResponse> purchaseOrderSummaryModelResponsePage = pagedResourcePurchaseOrderAssembler
				.toModel(purchaseOrdersPage, purchaseOrderSummaryAssembler);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(purchaseOrderSummaryModelResponsePage));
	}
	
	@CheckSecurity.PurchaseOrders.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<PurchaseOrderModelResponse>> findById(@PathVariable("id") Long id) {
		PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);

		PurchaseOrderModelResponse purchaseOrderModelResponse = purchaseOrderAssembler.toModel(purchaseOrder);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(purchaseOrderModelResponse));
	}
	
}
