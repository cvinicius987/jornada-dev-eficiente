package com.jornada.casadocodigo.delivery.api.book;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada.casadocodigo.core.domain.book.Book;

@RestController
@RequestMapping("/books")
public class BookController {
	
	private EntityManager entityManager;
			
	//1
	public BookController(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@GetMapping
	public ResponseEntity<List<ListBookResponse>> list() {
		
		var listBooks = entityManager.createQuery("SELECT b FROM Book b ORDER BY b.title", Book.class).getResultList();
		
		//2
		var result = listBooks.stream()
							 .map(book -> new ListBookResponse(book.getId(), book.getTitle()))
							 .collect(Collectors.toList());
					
		return ResponseEntity.ok(result);
	}
	
	//3
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveBookRequest request) {
		
		var book = request.toModel(entityManager);
		
		this.entityManager.persist(book);
				
		return ResponseEntity.ok(book.toString());
	}
}
