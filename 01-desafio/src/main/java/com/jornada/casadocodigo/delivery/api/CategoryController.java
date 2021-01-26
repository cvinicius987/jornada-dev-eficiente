package com.jornada.casadocodigo.delivery.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada.casadocodigo.core.domain.category.CategoryRepository;
import com.jornada.casadocodigo.delivery.request.SaveCategoryRequest;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
	
	private CategoryRepository categoryRepository;
			
	//1
	public CategoryController(CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
	}
		
	//2
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveCategoryRequest request) {
		
		var category = request.toModel();
		
		this.categoryRepository.save(category);
				
		return ResponseEntity.ok(category.toString());
	}
}
