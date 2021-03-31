package com.jornada.casadocodigo.delivery.api.book.response;

public final class ListBookResponse {

	private Long id;
	private String title;
	
	public ListBookResponse(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
}
