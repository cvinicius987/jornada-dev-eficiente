package com.jornada.casadocodigo.delivery.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NotRepeatValidatorTest {

	private EntityManager entityManager;
	
	private Query query;
	
	private ConstraintValidatorContext constraintValidatorContext;
	
	private NotRepeatValidator notRepeatValidator;
	
	@BeforeEach
	public void prepare() {
		
		entityManager 				= Mockito.mock(EntityManager.class);
		query		 				= Mockito.mock(Query.class);
		constraintValidatorContext 	= Mockito.mock(ConstraintValidatorContext.class);
		notRepeatValidator 			= new NotRepeatValidator(entityManager);
	}

	@DisplayName("Validação do campo name, em caso de nulo retorna true")
	@Test
	public void isValid_nameNull_returnTrueTest() 
	throws NoSuchFieldException, SecurityException {
		
		var obj = new ObjToTest(null, 1);
		
		notRepeatValidator.initialize(obj.getClass().getDeclaredField("name").getAnnotation(NotRepeatValue.class));
		
		var result = notRepeatValidator.isValid(obj.getName(), constraintValidatorContext);

		assertTrue(result);
	}
	
	@DisplayName("Validação do campo name, name não existe na base, retorna true")
	@Test
	public void isValid_name_NotExists_InDatabase_returnTrueTest() 
	throws NoSuchFieldException, SecurityException {
		
		var obj = new ObjToTest("name", 1);
				
		notRepeatValidator.initialize(obj.getClass().getDeclaredField("name").getAnnotation(NotRepeatValue.class));
		
		Mockito.when(entityManager.createQuery("SELECT 1 FROM ObjToTest WHERE name = :value")).thenReturn(query);
		Mockito.when(query.setParameter("value", (Object)"name")).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
		
		var result = notRepeatValidator.isValid(obj.getName(), constraintValidatorContext);

		assertTrue(result);
	}
	
	@DisplayName("Validação do campo name, name já existe na base, retorna false")
	@Test
	public void isValid_name_exists_InDatabase_returnFalseTest() 
	throws NoSuchFieldException, SecurityException {
		
		var obj = new ObjToTest("name", 1);
		
		notRepeatValidator.initialize(obj.getClass().getDeclaredField("name").getAnnotation(NotRepeatValue.class));
		
		Mockito.when(entityManager.createQuery("SELECT 1 FROM ObjToTest WHERE name = :value")).thenReturn(query);
		Mockito.when(query.setParameter("value", (Object)"name")).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(List.of(new ObjToTest("name", 1)));
		
		var result = notRepeatValidator.isValid(obj.getName(), constraintValidatorContext);

		assertFalse(result);
	}	
}