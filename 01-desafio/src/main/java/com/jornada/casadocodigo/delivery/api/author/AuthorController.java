package com.jornada.casadocodigo.delivery.api.author;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	private EntityManager entityManager;
			
	//1
	public AuthorController(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	//2
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveAuthorRequest request) {
		
		var author = request.toModel();
		
		this.entityManager.persist(author);
				
		return ResponseEntity.ok(author.toString());
	}
}
