package com.jornada.casadocodigo.delivery.api.book.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jornada.casadocodigo.core.domain.book.Book;

public class ViewBookResponse {

	private Long id;
	
	private String title, resume, summary, isbn, categoryName, authorName, authorDescription;

	private BigDecimal value;

	private Integer pages;

	private LocalDate date;
	
	public ViewBookResponse(Book book) {
		super();
		this.id = book.getId();
		this.title = book.getTitle();
		this.resume = book.getResume();
		this.summary = book.getSummary();
		this.isbn = book.getIsbn();
		this.categoryName = book.getCategory().getName();
		this.authorName = book.getAuthor().getName();
		this.authorDescription = book.getAuthor().getDescription();
		this.value = book.getValue();
		this.pages = book.getPages();
		this.date = book.getDate();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getResume() {
		return resume;
	}

	public String getSummary() {
		return summary;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getAuthorDescription() {
		return authorDescription;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Integer getPages() {
		return pages;
	}

	public LocalDate getDate() {
		return date;
	}
	
}