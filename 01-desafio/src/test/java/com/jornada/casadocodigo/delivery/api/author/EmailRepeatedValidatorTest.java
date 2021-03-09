package com.jornada.casadocodigo.delivery.api.author;

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

import com.jornada.casadocodigo.delivery.api.author.request.SaveAuthorRequest;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValidator;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValue;

public class EmailRepeatedValidatorTest {

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

	@DisplayName("Validação do campo email, em caso de nulo retorna true")
	@Test
	public void isValid_emailNull_returnTrueTest() 
	throws NoSuchFieldException, SecurityException {
		
		var saveAuthorRequest = new SaveAuthorRequest("name", null, "description");
		
		notRepeatValidator.initialize(saveAuthorRequest.getClass().getDeclaredField("email").getAnnotation(NotRepeatValue.class));
		
		var result = notRepeatValidator.isValid(saveAuthorRequest.getEmail(), constraintValidatorContext);

		assertTrue(result);
	}
	
	@DisplayName("Validação do campo email, email não existe na base")
	@Test
	public void isValid_emailNotExistsInDatabaseTest() 
	throws NoSuchFieldException, SecurityException {
		
		var saveAuthorRequest = new SaveAuthorRequest("name", "teste@teste.com.br", "description");
		
		notRepeatValidator.initialize(saveAuthorRequest.getClass().getDeclaredField("email").getAnnotation(NotRepeatValue.class));
		
		Mockito.when(entityManager.createQuery("SELECT 1 FROM Author WHERE email = :value")).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
		
		var result = notRepeatValidator.isValid(saveAuthorRequest.getEmail(), constraintValidatorContext);

		assertTrue(result);
	}
	
	@DisplayName("Validação do campo email, email já existe na base")
	@Test
	public void isValid_emailExistsInDatabaseTest() 
	throws NoSuchFieldException, SecurityException {
		
		var saveAuthorRequest = new SaveAuthorRequest("name", "teste@teste.com.br", "description");
		
		notRepeatValidator.initialize(saveAuthorRequest.getClass().getDeclaredField("email").getAnnotation(NotRepeatValue.class));
		
		Mockito.when(entityManager.createQuery("SELECT 1 FROM Author WHERE email = :value")).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(List.of(""));
		
		var result = notRepeatValidator.isValid(saveAuthorRequest.getEmail(), constraintValidatorContext);

		assertFalse(result);
	}
}