package com.jornada.casadocodigo.delivery.api.category;

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

import com.jornada.casadocodigo.delivery.validator.NotRepeatValidator;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValue;

public class CategoryNameRepeatedValidatorTest {

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
	public void isValid_categoryNameNull_returnTrueTest() 
	throws NoSuchFieldException, SecurityException {
		
		var saveCategoryRequest = new SaveCategoryRequest(null);
		
		notRepeatValidator.initialize(saveCategoryRequest.getClass().getDeclaredField("name").getAnnotation(NotRepeatValue.class));
		
		var result = notRepeatValidator.isValid(saveCategoryRequest.getName(), constraintValidatorContext);

		assertTrue(result);
	}
	
	@DisplayName("Validação do campo name, name não existe na base")
	@Test
	public void isValid_nameNotExistsInDatabaseTest() 
	throws NoSuchFieldException, SecurityException {
		
		var saveCategoryRequest = new SaveCategoryRequest("name");
				
		notRepeatValidator.initialize(saveCategoryRequest.getClass().getDeclaredField("name").getAnnotation(NotRepeatValue.class));
		
		Mockito.when(entityManager.createQuery("SELECT 1 FROM Category WHERE name = :value")).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
		
		var result = notRepeatValidator.isValid(saveCategoryRequest.getName(), constraintValidatorContext);

		assertTrue(result);
	}
	
	@DisplayName("Validação do campo name, name já existe na base")
	@Test
	public void isValid_emailExistsInDatabaseTest() 
	throws NoSuchFieldException, SecurityException {
		
		var saveCategoryRequest = new SaveCategoryRequest("name");
		
		notRepeatValidator.initialize(saveCategoryRequest.getClass().getDeclaredField("name").getAnnotation(NotRepeatValue.class));
		
		Mockito.when(entityManager.createQuery("SELECT 1 FROM Category WHERE name = :value")).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(List.of(""));
		
		var result = notRepeatValidator.isValid(saveCategoryRequest.getName(), constraintValidatorContext);

		assertFalse(result);
	}
}