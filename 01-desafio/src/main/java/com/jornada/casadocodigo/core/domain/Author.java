package com.jornada.casadocodigo.core.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="tbl_author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(max=100)
	private String name;
	
	@NotEmpty
	@Size(max=50)
	private String email;
	
	@NotEmpty
	@Size(max=400)
	private String description;
	
	private LocalDateTime created = LocalDateTime.now();

	public Author(String name, String email, String description) {
		this.name = name;
		this.email = email;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getDescription() {
		return description;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", email=" + email + ", description=" + description + "]";
	}	
}