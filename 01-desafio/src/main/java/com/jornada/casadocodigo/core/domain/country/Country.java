package com.jornada.casadocodigo.core.domain.country;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.jornada.casadocodigo.core.domain.country.state.State;

@Entity
@Table(name="tbl_country")
public class Country {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	@Size(max = 50)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	private List<State> states;
	
	public Country() {}
	
	public Country(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}
}