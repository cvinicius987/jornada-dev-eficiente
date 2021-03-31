package com.jornada.casadocodigo.delivery.api.country.request;

import javax.validation.constraints.NotEmpty;

import com.jornada.casadocodigo.core.domain.country.Country;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValue;

public class SaveCountryRequest {

	@NotRepeatValue(entityClass = Country.class, fieldName = "name")
	@NotEmpty
	private String name;
		
	public void setName(String name) {
		this.name = name;
	}

	public Country toModel() {
		return new Country(name);
	}
}