package com.jornada.casadocodigo.delivery;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponse {

	private final List<FieldValidationResponse> errors = new ArrayList<>();
	
	public void addError(FieldValidationResponse field) {
		this.errors.add(field);
	}

	public List<FieldValidationResponse> getErrors(){
		return errors;
	}
}