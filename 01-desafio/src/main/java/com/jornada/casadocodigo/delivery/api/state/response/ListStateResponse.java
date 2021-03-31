package com.jornada.casadocodigo.delivery.api.state.response;

import com.jornada.casadocodigo.core.domain.country.state.State;

public class ListStateResponse {

	private Long id;
	
	private String name;
	
	private String country;

	public ListStateResponse(State c) {
		super();
		this.id = c.getId();
		this.name = c.getName();
		this.country= c.getCountry().getName();
	}
	
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}
}