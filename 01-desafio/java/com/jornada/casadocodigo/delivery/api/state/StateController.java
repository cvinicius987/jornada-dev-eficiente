package com.jornada.casadocodigo.delivery.api.state;

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

import com.jornada.casadocodigo.core.domain.country.state.State;
import com.jornada.casadocodigo.delivery.api.state.request.SaveStateRequest;
import com.jornada.casadocodigo.delivery.api.state.response.ListStateResponse;

@RestController
@RequestMapping("/states")
public class StateController {
	
	private EntityManager entityManager;
			
	//1
	public StateController(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@GetMapping
	public ResponseEntity<List<ListStateResponse>> list() {
		
		var list = entityManager.createQuery("SELECT b FROM State b JOIN FETCH b.country c ORDER BY b.name", State.class).getResultList();
				
		var result = list.stream()
							 .map(ListStateResponse::new) //2
							 .collect(Collectors.toList());
					
		return ResponseEntity.ok(result);
	}
	
	//3
	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveStateRequest request) {
		
		var state = request.toModel(entityManager);
				
		this.entityManager.persist(state);
				
		return ResponseEntity.ok(state.toString());
	}
	
	//4
	@GetMapping("/{id}")
	public ResponseEntity<ListStateResponse> view(@PathVariable Long id) {
		
		var query = "SELECT b FROM State b JOIN FETCH b.country c WHERE b.id = :id";
		
		var typedQuery = entityManager.createQuery(query, State.class);
		
		typedQuery.setParameter("id", id);
		
		//5
		ResponseEntity<ListStateResponse> result = ResponseEntity.notFound().build();
		
		try { //6
			result = ResponseEntity.ok(new ListStateResponse(typedQuery.getSingleResult()));
			
		}catch(NoResultException nex) {} //7
		
		return result;
	}
}