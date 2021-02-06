package com.jornada.casadocodigo.core.domain.book;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jornada.casadocodigo.core.domain.author.Author;
import com.jornada.casadocodigo.core.domain.category.Category;

@Entity
@Table(name="tbl_book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	@Size(max = 500)
	private String resume;
	
	@NotEmpty
	private String summary;
	
	@NotNull
	private BigDecimal value;
	
	@NotNull
	private Integer pages;
	
	@NotEmpty
	private String isbn;

	@Future
	private LocalDate date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Author author;
		
	public Book() {}
			
	public Book(@NotEmpty String title, @NotEmpty @Size(max = 500) String resume, @NotEmpty String summary,
			BigDecimal value, @NotNull Integer pages, @NotEmpty String isbn, @Future LocalDate date,
			@NotNull Category category, @NotNull Author author) {
		super();
		this.title = title;
		this.resume = resume;
		this.summary = summary;
		this.value = value;
		this.pages = pages;
		this.isbn = isbn;
		this.date = date;
		this.category = category;
		this.author = author;
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

	public BigDecimal getValue() {
		return value;
	}

	public Integer getPages() {
		return pages;
	}

	public String getIsbn() {
		return isbn;
	}


	public Category getCategory() {
		return category;
	}

	public Author getAuthor() {
		return author;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getSummary() {
		return summary;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", resume=" + resume + ", summary=" + summary + ", value="
				+ value + ", pages=" + pages + ", isbn=" + isbn + ", date=" + date + ", category=" + category
				+ ", author=" + author + "]";
	}
}