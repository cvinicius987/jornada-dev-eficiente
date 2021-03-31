package com.jornada.casadocodigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public MessageSource messageSource() {
		
	    var messageSource = new ReloadableResourceBundleMessageSource();
	    
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("ISO-8859-1");
	    
	    return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		
	    var bean = new LocalValidatorFactoryBean();
	    
	    bean.setValidationMessageSource(messageSource());
	    
	    return bean;
	}
}