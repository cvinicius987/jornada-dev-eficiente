package com.jornada.casadocodigo.delivery.api.book;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada.casadocodigo.core.domain.book.Book;
import com.jornada.casadocodigo.delivery.api.book.response.ListBookResponse;
import com.jornada.casadocodigo.delivery.api.book.response.ViewBookResponse;

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
				
		var result = listBooks.stream()
							 .map(book -> new ListBookResponse(book.getId(), book.getTitle())) //2
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
	
	//4
	@GetMapping("/{id}")
	public ResponseEntity<ViewBookResponse> view(@PathVariable Long id) {
		
		var query = "SELECT b FROM Book b JOIN FETCH b.author a JOIN FETCH b.category c WHERE b.id = :id";
		
		var typedQuery = entityManager.createQuery(query, Book.class);
		
		typedQuery.setParameter("id", id);
		
		//5
		ResponseEntity<ViewBookResponse> result = ResponseEntity.notFound().build();
		
		try { //6
			Book bookResult = typedQuery.getSingleResult();
			
			result = ResponseEntity.ok(new ViewBookResponse(bookResult));
			
		}catch(NoResultException nex) {} //7
		
		return result;
		
	}
}