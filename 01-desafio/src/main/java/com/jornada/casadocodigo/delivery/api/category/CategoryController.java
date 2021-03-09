package com.jornada.casadocodigo.delivery.api.category;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada.casadocodigo.delivery.api.category.request.SaveCategoryRequest;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
	
	private EntityManager entityManager;
			
	//1
	public CategoryController(EntityManager entityManager){
		this.entityManager = entityManager;
	}
		
	//2
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveCategoryRequest request) {
		
		var category = request.toModel();
		
		this.entityManager.persist(category);
				
		return ResponseEntity.ok(category.toString());
	}
}
