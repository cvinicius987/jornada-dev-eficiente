package com.jornada.casadocodigo.delivery.request;

import javax.validation.constraints.NotEmpty;

import com.jornada.casadocodigo.core.domain.category.Category;

public class SaveCategoryRequest {

	@NotEmpty
	private String name;

	public SaveCategoryRequest() {}
	
	public SaveCategoryRequest(String name) {
		super();
		this.name = name;
	}

	public Category toModel(){
		return new Category(name);
	}

	public String getName() {
		return name;
	}
}