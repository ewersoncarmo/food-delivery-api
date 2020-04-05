package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;
import com.smartcook.fooddeliveryapi.persistence.PaymentMethodRepository;
import com.smartcook.fooddeliveryapi.service.exception.ServiceException;

@Service
public class PaymentMethodService {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	public PaymentMethod create(PaymentMethod paymentMethod) {
		// M-13=Payment Method with Description {0} already exists.
		paymentMethodRepository.findByDescription(paymentMethod.getDescription())
			.ifPresent(s -> {
				throw new ServiceException("M-13", paymentMethod.getDescription()); 
				});
		
		return paymentMethodRepository.save(paymentMethod);
	}

	public List<PaymentMethod> findAll() {
		return paymentMethodRepository.findAll();
	}

	public PaymentMethod findById(Long id) {
		// M-12=No Payment Method with Id {0} was found.
		return paymentMethodRepository.findById(id).orElseThrow(() -> new ServiceException("M-12", id));
	}

	public PaymentMethod update(PaymentMethod paymentMethod) {
		// M-13=Payment Method with Description {0} already exists.
		paymentMethodRepository.findByDuplicatedDescription(paymentMethod.getDescription(), paymentMethod.getId())
			.ifPresent(s -> {
				throw new ServiceException("M-13", paymentMethod.getDescription()); 
				});
		
		return paymentMethodRepository.save(paymentMethod);
	}
	
	public void delete(Long id) {
		PaymentMethod paymentMethod = findById(id);

		// TODO check if payment method has relationships
		
		paymentMethodRepository.delete(paymentMethod);
	}

}
