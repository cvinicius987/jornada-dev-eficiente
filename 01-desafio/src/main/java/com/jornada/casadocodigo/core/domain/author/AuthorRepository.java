package com.jornada.casadocodigo.core.domain.author;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Optional<Author> findByEmail(String email);
}