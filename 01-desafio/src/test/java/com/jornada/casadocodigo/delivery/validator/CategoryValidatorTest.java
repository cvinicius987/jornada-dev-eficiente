package com.jornada.casadocodigo.delivery.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.jornada.casadocodigo.core.domain.category.CategoryRepository;
import com.jornada.casadocodigo.delivery.request.SaveCategoryRequest;

public class CategoryValidatorTest {

	private CategoryRepository categoryRepository;
	private MessageSource messageSource;
	private CategoryNameValidator categoryNameValidator;
	
	@BeforeEach
	public void prepare() {
		
		categoryRepository = Mockito.mock(CategoryRepository.class);
		messageSource 	 = Mockito.mock(MessageSource.class);
		
		categoryNameValidator = new CategoryNameValidator(categoryRepository, messageSource);
	}

	@DisplayName("Possui erros anteriores")
	@Test
	public void validateTest() {
		
		var saveCategoryRequest = new SaveCategoryRequest("");
		
		Errors errors = new BeanPropertyBindingResult(saveCategoryRequest, "saveCategoryRequest");
		
		errors.reject("name");
		
		categoryNameValidator.validate(saveCategoryRequest, errors);
		
		Mockito.verify(categoryRepository, times(0)).existsByName(anyString());
		Mockito.verify(messageSource, times(0)).getMessage(anyString(), eq(null), eq(LocaleContextHolder.getLocale()));
	}
	
	@DisplayName("Nome válido e inexistente na base")
	@Test
	public void validateNameTest() {
		
		when(categoryRepository.existsByName("teste")).thenReturn(false);
		
		var saveCategoryRequest = new SaveCategoryRequest("name");
		
		Errors errors = new BeanPropertyBindingResult(saveCategoryRequest, "saveCategoryRequest");
		
		categoryNameValidator.validate(saveCategoryRequest, errors);
		
		Mockito.verify(messageSource, times(0)).getMessage(anyString(), eq(null), eq(LocaleContextHolder.getLocale()));
	}
	
	@DisplayName("Nome já existe na base")
	@Test
	public void validateEmailRepeatedTest() {
		
		when(categoryRepository.existsByName("name")).thenReturn(true);
		when(messageSource.getMessage("Name.duplicated", null, LocaleContextHolder.getLocale())).thenReturn("Nome já existe.");
		
		var saveCategoryRequest = new SaveCategoryRequest("name");
		
		Errors errors = new BeanPropertyBindingResult(saveCategoryRequest, "saveCategoryRequest");
		
		categoryNameValidator.validate(saveCategoryRequest, errors);
		
		assertSame(1, errors.getErrorCount());
		assertEquals("name", errors.getFieldError("name").getField());
		assertEquals("saveCategoryRequest", errors.getFieldError("name").getObjectName());
		assertEquals("Nome já existe.", errors.getFieldError("name").getDefaultMessage());
	}
}