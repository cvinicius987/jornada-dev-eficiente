package com.jornada.casadocodigo.delivery.api.country.response;

import com.jornada.casadocodigo.core.domain.country.Country;

public class ListCountryResponse {

	private Long id;
	private String name;
	
	public ListCountryResponse(Country c) {
		super();
		this.id = c.getId();
		this.name = c.getName();
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}