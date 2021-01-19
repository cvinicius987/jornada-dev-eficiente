package com.jornada.casadocodigo.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorAdviceController {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ValidationResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		var fieldErrors = ex.getBindingResult().getFieldErrors();
		
		return defaultError(fieldErrors);
	}
		
	private ValidationResponse defaultError(List<FieldError> fieldErrors) {

		//1
		var messageValidationResponse = new ValidationResponse();
				
		//2
		if(!fieldErrors.isEmpty()) {
			
			//3
			fieldErrors.forEach(fieldError -> {
				
				var message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
				
				//4
				messageValidationResponse.addError(new FieldValidationResponse(fieldError.getField(), message));
			});
		}
		
		return messageValidationResponse;
	}
}