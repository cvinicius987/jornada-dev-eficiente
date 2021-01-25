package com.jornada.casadocodigo.delivery.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.jornada.casadocodigo.core.domain.author.Author;
import com.jornada.casadocodigo.core.domain.author.AuthorRepository;
import com.jornada.casadocodigo.delivery.request.SaveAuthorRequest;

public class EmailValidatorTest {

	private AuthorRepository authorRepository;
	private MessageSource messageSource;
	private EmailValidator emailValidator;
	
	@BeforeEach
	public void prepare() {
		
		authorRepository = Mockito.mock(AuthorRepository.class);
		messageSource 	 = Mockito.mock(MessageSource.class);
		
		emailValidator = new EmailValidator(authorRepository, messageSource);
	}

	@DisplayName("Possui erros anteriores")
	@Test
	public void validateTest() {
		
		var saveAuthorRequest = new SaveAuthorRequest("", "", "");
		
		Errors errors = new BeanPropertyBindingResult(saveAuthorRequest, "saveAuthorRequest");
		
		errors.reject("name");
		
		emailValidator.validate(saveAuthorRequest, errors);
		
		Mockito.verify(authorRepository, times(0)).findByEmail(anyString());
		Mockito.verify(messageSource, times(0)).getMessage(anyString(), eq(null), eq(LocaleContextHolder.getLocale()));
	}
	
	@DisplayName("Não possui email duplicado")
	@Test
	public void validateEmailTest() {
		
		when(authorRepository.findByEmail("teste@teste.com.br")).thenReturn(Optional.empty());
		
		var saveAuthorRequest = new SaveAuthorRequest("name", "teste@teste.com.br", "descricao");
		
		Errors errors = new BeanPropertyBindingResult(saveAuthorRequest, "saveAuthorRequest");
		
		emailValidator.validate(saveAuthorRequest, errors);
		
		Mockito.verify(messageSource, times(0)).getMessage(anyString(), eq(null), eq(LocaleContextHolder.getLocale()));
	}
	
	@DisplayName("Possui email duplicado")
	@Test
	public void validateEmailRepeatedTest() {
		
		when(authorRepository.findByEmail("teste@teste.com.br")).thenReturn(Optional.of(new Author("teste", "teste@teste.com.br", "description")));
		when(messageSource.getMessage("Email.repeated", null, LocaleContextHolder.getLocale())).thenReturn("E-mail já esta cadastrado");
		
		var saveAuthorRequest = new SaveAuthorRequest("name", "teste@teste.com.br", "descricao");
		
		Errors errors = new BeanPropertyBindingResult(saveAuthorRequest, "saveAuthorRequest");
		
		emailValidator.validate(saveAuthorRequest, errors);
		
		assertSame(1, errors.getErrorCount());
		assertEquals("email", errors.getFieldError("email").getField());
		assertEquals("saveAuthorRequest", errors.getFieldError("email").getObjectName());
		assertEquals("E-mail já esta cadastrado", errors.getFieldError("email").getDefaultMessage());
	}
}