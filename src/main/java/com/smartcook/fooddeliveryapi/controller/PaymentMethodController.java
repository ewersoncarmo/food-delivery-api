package com.smartcook.fooddeliveryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping
	public ResponseEntity<ModelResponse<List<PaymentMethodModelResponse>>> findAll() {
		List<PaymentMethod> states = paymentMethodService.findAll();

		List<PaymentMethodModelResponse> stateModelResponse = paymentMethodAssembler.toCollectionModel(states);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(stateModelResponse));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ModelResponse<PaymentMethodModelResponse>> findById(@PathVariable("id") Long id) {
		PaymentMethod paymentMethod = paymentMethodService.findById(id);

		PaymentMethodModelResponse stateModelResponse = paymentMethodAssembler.toModel(paymentMethod);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(ModelResponse.withData(stateModelResponse));
	}
	
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		paymentMethodService.delete(id);
		
		return ResponseEntity.noContent()
				.build();
	}
}
