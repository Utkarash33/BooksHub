package com.books.services;

import java.util.List;

import com.books.entities.Author;

public interface AuthorService {

	Author createAuthor(String name, String bio);

	Author updateAuthor(String bio, Integer authorId);

	String deleteAuthor(Integer authorId);

	List<Author> getAllAuthors();
	
	

}
