package com.jornada.casadocodigo.delivery.validator;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jornada.casadocodigo.core.domain.category.Category;

@Entity
class ObjToTest {
	
	@NotRepeatValue(entityClass = ObjToTest.class, fieldName = "name")
	@NotEmpty
	private String name;
	
	@ExistsIdValue(entityClass = ObjToTest.class, fieldName = "id")
	@NotNull
	private Integer id;

	public ObjToTest(String name, Integer id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}
	
}

