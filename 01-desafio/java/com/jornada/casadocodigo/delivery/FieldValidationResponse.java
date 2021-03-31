package com.jornada.casadocodigo.delivery;

public class FieldValidationResponse {
	
	private String field, message;
	
	public FieldValidationResponse(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
}
