package com.jornada.casadocodigo.delivery.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.jornada.casadocodigo.core.domain.Author;

public class SaveAuthorRequest {

	@NotEmpty(message="Nome esta vazio.")
	private String name;
	
	@Email(message="E-Mail invalido.")
	@NotEmpty(message="E-Mail esta vazia.")
	private String email;
	
	@NotEmpty(message="Descrição esta vazia.") 
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
}