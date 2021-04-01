package com.jornada.casadocodigo.delivery.api.payment.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class PaymentRequest {

	@NotEmpty
	@Email(message="Email com formato invalido")
	private String email;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String lastname;
	
	@NotEmpty
	private String doc;
	
	@NotEmpty
	private String address;
	
	@NotEmpty
	private String comp;
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String country;
	
	private String state;
	
	@NotEmpty
	private String phone;
	
	@NotEmpty
	private String cep;
	
	public PaymentRequest(@NotEmpty @Email(message = "Email com formato invalido") String email, @NotEmpty String name,
			@NotEmpty String lastname, @NotEmpty String doc, @NotEmpty String address, @NotEmpty String comp,
			@NotEmpty String city, @NotEmpty String country, String state, @NotEmpty String phone,
			@NotEmpty String cep) {
		super();
		this.email = email;
		this.name = name;
		this.lastname = lastname;
		this.doc = doc;
		this.address = address;
		this.comp = comp;
		this.city = city;
		this.country = country;
		this.state = state;
		this.phone = phone;
		this.cep = cep;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}
	
	
}