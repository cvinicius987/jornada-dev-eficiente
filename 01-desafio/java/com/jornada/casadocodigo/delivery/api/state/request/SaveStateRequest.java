package com.jornada.casadocodigo.delivery.api.state.request;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jornada.casadocodigo.core.domain.country.Country;
import com.jornada.casadocodigo.core.domain.country.state.State;
import com.jornada.casadocodigo.delivery.validator.ExistsIdValue;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValue;

public class SaveStateRequest {

	@NotRepeatValue(entityClass = State.class, fieldName = "name")
	@NotEmpty
	@Size(max = 50)
	private String name;
	
	@ExistsIdValue(entityClass = Country.class, fieldName = "id")
	@NotNull
	private Long countryId;
	
	public SaveStateRequest(@NotEmpty @Size(max = 50) String name, @NotNull Long countryId) {
		super();
		this.name = name;
		this.countryId = countryId;
	}

	public State toModel(EntityManager manager) {
		
		Country country = manager.find(Country.class, countryId);
				
		return new State(name, country);
	}
}