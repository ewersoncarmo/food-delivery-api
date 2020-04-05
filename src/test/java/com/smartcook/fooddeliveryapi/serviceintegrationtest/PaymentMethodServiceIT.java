package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcook.fooddeliveryapi.domain.entity.PaymentMethod;
import com.smartcook.fooddeliveryapi.service.PaymentMethodService;

public class PaymentMethodServiceIT extends AbstractTransactionalServiceTest {

	@Autowired
	private PaymentMethodService paymentMethodService;
	
	private PaymentMethod creditCard;
	
	@BeforeEach
	public void setUp() {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setDescription("Credit Card");
		
		this.creditCard = paymentMethod;
	}
	
	@Test
	public void shouldSucceed_WhenCreateAValidPaymentMethod() {
		paymentMethodService.create(creditCard);
		assertNotNull(creditCard.getId());
	}
	
	@Test
	public void shouldFailOnCreate_WhenAlreadyExistsAPaymentMethodWithTheSameName() {
		paymentMethodService.create(creditCard);

		PaymentMethod duplicatedPaymentMethod = new PaymentMethod();
		duplicatedPaymentMethod.setDescription("Credit Card");
		
		assertThatThrownBy(() -> {
			paymentMethodService.create(duplicatedPaymentMethod);
		}).hasMessageContaining("M-13");
	}
	
	@Test
	public void shouldSucceed_WhenFindAllPaymentMethods() {
		paymentMethodService.create(creditCard);

		PaymentMethod debitCard = new PaymentMethod();
		debitCard.setDescription("Debit Card");
		
		paymentMethodService.create(debitCard);

		List<PaymentMethod> list = paymentMethodService.findAll();
		
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldSucceed_WhenFindAnExistingPaymentMethod() {
		paymentMethodService.create(creditCard);
		assertNotNull(creditCard.getId());
		
		PaymentMethod result = paymentMethodService.findById(creditCard.getId());
		assertEquals(creditCard, result);
	}
	
	@Test
	public void shouldFail_WhenFindAnUnexistingPaymentMethod() {
		assertThatThrownBy(() -> {
			paymentMethodService.findById(1L);
		}).hasMessageContaining("M-12");
	}
	
	@Test
	public void shouldSucceed_WhenUpdateAnExistingPaymentMethod() {
		paymentMethodService.create(creditCard);
		
		creditCard.setDescription("Debit Card");
		
		paymentMethodService.update(creditCard);
		
		assertEquals("Debit Card", creditCard.getDescription());
	}
	
	@Test
	public void shouldFailOnUpdate_WhenAlreadyExistsAPaymentMethodWithTheSameName() {
		paymentMethodService.create(creditCard);

		PaymentMethod debitCard = new PaymentMethod();
		debitCard.setDescription("Debit Card");
		
		paymentMethodService.create(debitCard);
		
		debitCard.setDescription("Credit Card");
		
		assertThatThrownBy(() -> {
			paymentMethodService.update(debitCard);
		}).hasMessageContaining("M-13");
	}
	
	@Test
	public void shouldSucceed_WhenDeleteAnExistingPaymentMethod() {
		paymentMethodService.create(creditCard);
		
		paymentMethodService.delete(creditCard.getId());
		
		assertThatThrownBy(() -> {
			paymentMethodService.findById(creditCard.getId());
		}).hasMessageContaining("M-12");
	}
	
	@Test
	public void shouldFail_WhenDeleteAnUnexistingPaymentMethod() {
		assertThatThrownBy(() -> {
			paymentMethodService.delete(1L);
		}).hasMessageContaining("M-12");
	}
}
