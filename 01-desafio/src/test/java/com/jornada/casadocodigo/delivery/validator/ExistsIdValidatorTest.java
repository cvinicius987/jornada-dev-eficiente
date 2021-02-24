package com.jornada.casadocodigo.delivery.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ExistsIdValidatorTest {

	private EntityManager entityManager;
	
	private TypedQuery query;
	
	private ConstraintValidatorContext constraintValidatorContext;
	
	private ExistsIdValidator existsIdValidator;
	
	@BeforeEach
	public void prepare() {
		
		entityManager 				= Mockito.mock(EntityManager.class);
		query		 				= Mockito.mock(TypedQuery.class);
		constraintValidatorContext 	= Mockito.mock(ConstraintValidatorContext.class);
		existsIdValidator 			= new ExistsIdValidator(entityManager);
	}

	@DisplayName("Caso seja nulo, retorna false")
	@Test
	public void isValid_id_as_null_return_falseTest() 
	throws NoSuchFieldException, SecurityException {
		
		var obj = new ObjToTest("name", null);
		
		existsIdValidator.initialize(obj.getClass().getDeclaredField("id").getAnnotation(ExistsIdValue.class));
		
		var result = existsIdValidator.isValid(obj.getId(), constraintValidatorContext);

		assertFalse(result);
	}
}