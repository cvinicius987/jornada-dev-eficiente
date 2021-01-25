package com.jornada.casadocodigo.delivery.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jornada.casadocodigo.core.domain.author.AuthorRepository;
import com.jornada.casadocodigo.delivery.request.SaveAuthorRequest;

@Component
public class EmailValidator implements Validator{

	private AuthorRepository authorRepository;
	private MessageSource messageSource;
	
	//1
	public EmailValidator(AuthorRepository authorRepository, MessageSource messageSource) {
		this.authorRepository 	= authorRepository;
		this.messageSource 		= messageSource;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		//2
		return SaveAuthorRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//3
		if(!errors.hasErrors()) {
			
			SaveAuthorRequest obj = (SaveAuthorRequest)target;
				
			//4
			var hasEmail = authorRepository.findByEmail(obj.getEmail());
			
			//5
			hasEmail.ifPresent(email -> {
				
				errors.rejectValue("email", null, messageSource.getMessage("Email.repeated", null, LocaleContextHolder.getLocale()));
			});
		}
	}
}