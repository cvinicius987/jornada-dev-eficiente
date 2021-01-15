package com.jornada.casadocodigo.delivery.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jornada.casadocodigo.core.domain.AuthorRepository;
import com.jornada.casadocodigo.delivery.request.SaveAuthorRequest;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	private AuthorRepository authorRepository;
	
	public AuthorController(AuthorRepository authorRepository){
		this.authorRepository = authorRepository;
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody SaveAuthorRequest request, UriComponentsBuilder b) {
		
		var author = request.toModel();
		
		this.authorRepository.save(author);
				
		return ResponseEntity.created(b.path("/authors/{id}").buildAndExpand(author.getId()).toUri())
							 .build();
	}
}
