package com.jornada.casadocodigo.delivery.api.payment;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada.casadocodigo.delivery.api.country.validator.CountryStateValidator;
import com.jornada.casadocodigo.delivery.api.payment.request.PaymentRequest;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private CountryStateValidator countryStateValidator;
	
	@InitBinder
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(countryStateValidator);
	}

	//3
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody PaymentRequest request) {

		return ResponseEntity.ok("");
	}
}