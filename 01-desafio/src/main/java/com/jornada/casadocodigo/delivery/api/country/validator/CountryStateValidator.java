package com.jornada.casadocodigo.delivery.api.country.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jornada.casadocodigo.delivery.api.payment.request.PaymentRequest;

@Component
public class CountryStateValidator implements Validator{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return PaymentRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(errors.hasErrors()) {
			return;
		}
		
		PaymentRequest obj = (PaymentRequest)target;
		
		//Regra
		var sql = " SELECT count(s) FROM State s JOIN s.country c WHERE c.name = :name ";
		
		TypedQuery<Long> query = entityManager.createQuery(sql , Long.class);
		
		query.setParameter("name", obj.getCountry());
		
		long total = query.getSingleResult();
		
		if(total > 0 && !StringUtils.hasLength(obj.getState())) {
			errors.rejectValue("state", "payment.state.invalid");
		}
		//
	}
}