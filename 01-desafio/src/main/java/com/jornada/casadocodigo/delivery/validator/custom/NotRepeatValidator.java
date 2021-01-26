package com.jornada.casadocodigo.delivery.validator.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotRepeatValidator implements ConstraintValidator<NotRepeatValue, Object> {
	
	private String fieldName;
	private Class<?> entityClass;
	
	@PersistenceContext
	private EntityManager manager;
		
	public NotRepeatValidator() {}
	
	public NotRepeatValidator(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void initialize(NotRepeatValue constraintAnnotation) {
		this.entityClass = constraintAnnotation.entityClass();
		this.fieldName   = constraintAnnotation.fieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
	
		//1
		if(value != null) {
			
			//2
			var query = manager.createQuery(String.format("SELECT 1 FROM %s WHERE %s = :value", entityClass.getSimpleName(), fieldName));
			
			query.setParameter("value", value);
			
			var result = query.getResultList();
			
			//3
			return result.size() > 0 ? false : true;
		}
		
		//4
		return true;
	}
}