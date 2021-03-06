package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartcook.fooddeliveryapi.controller.security.CheckSecurity;
import com.smartcook.fooddeliveryapi.domain.assembler.PaymentMethodAssembler;
import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;
import com.smartcook.fooddeliveryapi.domain.model.request.PaymentMethodModelRequest;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.domain.model.response.PaymentMethodModelResponse;
import com.smartcook.fooddeliveryapi.service.PaymentMethodService;

@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@Autowired
	private PaymentMethodAssembler paymentMethodAssembler;
	
	@CheckSecurity.PaymentMethods.CanEdit
	@PostMapping
	public ResponseEntity<ModelResponse<PaymentMethodModelResponse>> create(@Valid @RequestBody PaymentMethodModelRequest paymentMethodModelRequest) {
		PaymentMethod paymentMethod = paymentMethodAssembler.toEntity(paymentMethodModelRequest);
		
		paymentMethodService.create(paymentMethod);
		
		PaymentMethodModelResponse stateModelResponse = paymentMethodAssembler.toModel(paymentMethod);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(stateModelResponse.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(ModelResponse.withData(stateModelResponse));
	}
	
	@CheckSecurity.PaymentMethods.CanQuery
	@GetMapping
	public ResponseEntity<ModelResponse<CollectionModel<PaymentMethodModelResponse>>> findAll() {
		List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

		CollectionModel<PaymentMethodModelResponse> paymentMethodModelResponse = paymentMethodAssembler.toCollectionModel(paymentMethods);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(paymentMethodModelResponse));
	}
	
	@CheckSecurity.PaymentMethods.CanQuery
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<PaymentMethodModelResponse>> findById(@PathVariable("id") Long id) {
		PaymentMethod paymentMethod = paymentMethodService.findById(id);

		PaymentMethodModelResponse paymentMethodModelResponse = paymentMethodAssembler.toModel(paymentMethod);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(paymentMethodModelResponse));
	}
	
	@CheckSecurity.PaymentMethods.CanEdit
	@PutMapping("/{id}")
	public ResponseEntity<ModelResponse<PaymentMethodModelResponse>> update(@Valid @RequestBody PaymentMethodModelRequest paymentMethodModelRequest,
			@PathVariable("id") Long id) {
		PaymentMethod paymentMethod = paymentMethodService.findById(id);
		
		paymentMethodAssembler.copyToEntity(paymentMethodModelRequest, paymentMethod);
		
		paymentMethodService.update(paymentMethod);
		
		PaymentMethodModelResponse stateModelResponse = paymentMethodAssembler.toModel(paymentMethod);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(stateModelResponse));
	}
	
	@CheckSecurity.PaymentMethods.CanEdit
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		paymentMethodService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
