package com.jornada.casadocodigo.delivery.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jornada.casadocodigo.core.domain.category.CategoryRepository;
import com.jornada.casadocodigo.delivery.request.SaveCategoryRequest;

@Component
public class CategoryNameValidator implements Validator{

	private CategoryRepository categoryRepository;
	private MessageSource messageSource;
	
	//1
	public CategoryNameValidator(CategoryRepository CategoryRepository, MessageSource messageSource) {
		this.categoryRepository = CategoryRepository;
		this.messageSource 		= messageSource;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		//2
		return SaveCategoryRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//3
		if(!errors.hasErrors()) {
			
			SaveCategoryRequest obj = (SaveCategoryRequest)target;
				
			//4
			var hasEmail = categoryRepository.existsByName(obj.getName());
			
			//5
			if(hasEmail) {
				errors.rejectValue("name", null, messageSource.getMessage("Name.duplicated", null, LocaleContextHolder.getLocale()));
			}
		}
	}
}