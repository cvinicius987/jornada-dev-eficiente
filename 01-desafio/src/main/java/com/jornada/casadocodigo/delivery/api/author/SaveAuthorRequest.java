package com.jornada.casadocodigo.delivery.api.author;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.jornada.casadocodigo.core.domain.author.Author;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValue;

public class SaveAuthorRequest {

	@NotEmpty
	private String name;
	
	@NotRepeatValue(entityClass = Author.class, fieldName = "email")
	@Email
	@NotEmpty
	private String email;
	
	@NotEmpty 
	@Size(max=400, message="Descrição maior que {max}")
	private String description;

	public SaveAuthorRequest(String name, String email, String description) {
		super();
		this.name = name;
		this.email = email;
		this.description = description;
	}

	public Author toModel(){
		
		return new Author(name, email, description);
	}

	public String getEmail() {
		return email;
	}
}