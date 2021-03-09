package com.jornada.casadocodigo.delivery.api.book.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.jornada.casadocodigo.core.domain.author.Author;
import com.jornada.casadocodigo.core.domain.book.Book;
import com.jornada.casadocodigo.core.domain.category.Category;
import com.jornada.casadocodigo.delivery.validator.ExistsIdValue;
import com.jornada.casadocodigo.delivery.validator.NotRepeatValue;

public class SaveBookRequest {
	
	@NotRepeatValue(entityClass = Book.class, fieldName = "title")
	@NotEmpty
	private String title;
	
	@NotEmpty
	@Size(max = 500)
	private String resume;
	
	@NotNull
	@DecimalMin(message="{book.value.min}", value = "20.00")
	private BigDecimal value;
	
	@NotNull
	@Min(message="{book.value.min}", value = 100)
	private Integer pages;
	
	@NotRepeatValue(entityClass = Book.class, fieldName = "isbn")
	@NotEmpty
	private String isbn;
	
	@NotEmpty
	private String summary;
	
	@Future(message = "{book.date.future}")
	@JsonFormat(pattern = "yyyy-mm-dd", shape = Shape.STRING)
	private LocalDate date;
	
	@ExistsIdValue(entityClass = Category.class, fieldName = "id")
	@NotNull
	private Long categoryId;
	
	@ExistsIdValue(entityClass = Author.class, fieldName = "id")
	@NotNull
	private Long authorId;

	public SaveBookRequest(@NotEmpty String title, @NotEmpty @Size(max = 500) String resume,
			@NotNull @DecimalMin(message = "{book.value.min}", value = "20.00") BigDecimal value,
			@NotNull @Min(message = "{book.value.min}", value = 100) Integer pages, @NotEmpty String isbn,
			@NotEmpty String summary, @Future(message = "{book.date.future}") LocalDate date, @NotNull Long categoryId,
			@NotNull Long authorId) {
		super();
		this.title = title;
		this.resume = resume;
		this.value = value;
		this.pages = pages;
		this.isbn = isbn;
		this.summary = summary;
		this.date = date;
		this.categoryId = categoryId;
		this.authorId = authorId;
	}

	public Book toModel(EntityManager entityManager) {
		
		Author author 	  = entityManager.find(Author.class, authorId);
		Category category = entityManager.find(Category.class, categoryId);
		
		Assert.notNull(author, "Autor não existe, por favor verifique");
		Assert.notNull(category, "Categoria não existe, por favor verifique");
		
		return new Book(title, resume, summary, value, pages, isbn, date, category, author);
	}
}