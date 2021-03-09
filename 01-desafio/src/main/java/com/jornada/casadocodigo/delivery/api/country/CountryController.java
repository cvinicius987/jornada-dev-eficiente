package com.jornada.casadocodigo.delivery.api.country;

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

import com.jornada.casadocodigo.core.domain.country.Country;
import com.jornada.casadocodigo.delivery.api.country.request.SaveCountryRequest;
import com.jornada.casadocodigo.delivery.api.country.response.ListCountryResponse;

@RestController
@RequestMapping("/countrys")
public class CountryController {
	
	private EntityManager entityManager;
			
	//1
	public CountryController(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@GetMapping
	public ResponseEntity<List<ListCountryResponse>> list() {
		
		var list = entityManager.createQuery("SELECT b FROM Country b ORDER BY b.name", Country.class).getResultList();
				
		var result = list.stream()
							 .map(ListCountryResponse::new) //2
							 .collect(Collectors.toList());
					
		return ResponseEntity.ok(result);
	}
	
	//3
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveCountryRequest request) {
		
		var country = request.toModel();
				
		this.entityManager.persist(country);
				
		return ResponseEntity.ok(country.toString());
	}
	
	//4
	@GetMapping("/{id}")
	public ResponseEntity<ListCountryResponse> view(@PathVariable Long id) {
		
		var query = "SELECT b FROM Country b WHERE b.id = :id";
		
		var typedQuery = entityManager.createQuery(query, Country.class);
		
		typedQuery.setParameter("id", id);
		
		//5
		ResponseEntity<ListCountryResponse> result = ResponseEntity.notFound().build();
		
		try { //6
			result = ResponseEntity.ok(new ListCountryResponse(typedQuery.getSingleResult()));
			
		}catch(NoResultException nex) {} //7
		
		return result;
	}
}