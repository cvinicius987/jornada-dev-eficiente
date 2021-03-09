package com.jornada.casadocodigo.delivery.api.category.request;

import javax.validation.constraints.NotEmpty;

import com.jornada.casadocodigo.core.domain.category.Category;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValue;

public class SaveCategoryRequest {

	@NotRepeatValue(entityClass = Category.class, fieldName = "name")
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