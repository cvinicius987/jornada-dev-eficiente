package com.jornada.casadocodigo.delivery.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsIdValidator implements ConstraintValidator<ExistsIdValue, Object> {
	
	private String fieldName;
	private Class<?> entityClass;
	
	@PersistenceContext
	private EntityManager manager;
		
	public ExistsIdValidator() {}
	
	public ExistsIdValidator(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void initialize(ExistsIdValue constraintAnnotation) {
		this.entityClass = constraintAnnotation.entityClass();
		this.fieldName   = constraintAnnotation.fieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
	
		//1
		if(value != null) {
			
			var queryStr = String.format("SELECT case when (count(*) > 0)  then true else false end FROM %s WHERE %s = :value", entityClass.getSimpleName(), fieldName);
			
			//2
			var query = manager.createQuery(queryStr, Boolean.class);
			
			query.setParameter("value", value);
			
			return query.getSingleResult();
		}
		
		//3
		return false;
	}
}