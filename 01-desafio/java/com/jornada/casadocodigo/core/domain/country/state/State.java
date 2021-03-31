package com.jornada.casadocodigo.core.domain.country.state;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jornada.casadocodigo.core.domain.country.Country;

@Entity
@Table(name="tbl_state")
public class State {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	@Size(max = 50)
	private String name;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Country country;
	
	public State() {}
	
	public State(String name, Country country) {
		this.name = name;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}	

	public Country getCountry() {
		return country;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}
}