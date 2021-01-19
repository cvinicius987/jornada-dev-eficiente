package com.jornada.casadocodigo.delivery.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada.casadocodigo.core.domain.AuthorRepository;
import com.jornada.casadocodigo.delivery.request.SaveAuthorRequest;

@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	private AuthorRepository authorRepository;
	
	//1
	public AuthorController(AuthorRepository authorRepository){
		this.authorRepository = authorRepository;
	}
	
	//2
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveAuthorRequest request) {
		
		var author = request.toModel();
		
		this.authorRepository.save(author);
				
		return ResponseEntity.ok(author.toString());
	}
}
