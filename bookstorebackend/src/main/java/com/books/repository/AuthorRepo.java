package com.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.entities.Author;
import java.util.List;
import java.util.Optional;


public interface AuthorRepo extends JpaRepository<Author,Integer> {

	Optional<Author> findByName(String name);
	
}
