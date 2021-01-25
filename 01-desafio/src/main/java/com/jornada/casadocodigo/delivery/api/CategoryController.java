package com.jornada.casadocodigo.delivery.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jornada.casadocodigo.core.domain.category.CategoryRepository;
import com.jornada.casadocodigo.delivery.request.SaveCategoryRequest;
import com.jornada.casadocodigo.delivery.validator.CategoryNameValidator;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
	
	private CategoryRepository categoryRepository;
	private CategoryNameValidator categoryNameValidator;
			
	//1
	//2
	public CategoryController(CategoryRepository categoryRepository, CategoryNameValidator categoryNameValidator){
		this.categoryRepository = categoryRepository;
		this.categoryNameValidator = categoryNameValidator;
	}
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(categoryNameValidator);
	}
		
	//3
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveCategoryRequest request) {
		
		var category = request.toModel();
		
		this.categoryRepository.save(category);
				
		return ResponseEntity.ok(category.toString());
	}
}
