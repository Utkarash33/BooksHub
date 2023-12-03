package com.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.entities.Author;
import com.books.services.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
	
	
	@Autowired
	private AuthorService authorService;

	
	@PostMapping("/register")
	public ResponseEntity<Author> registerAutor(
			@RequestParam String name,
			@RequestParam String bio)
	{
		
   Author author = authorService.createAuthor(name,bio);
		
		return new ResponseEntity<>(author,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{authorId}")
	public ResponseEntity<Author> updateBio(
			@RequestParam String bio,
			@PathVariable Integer authorId)
	{
	
		Author author = authorService.updateAuthor(bio,authorId);
		
		return new ResponseEntity<>(author, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{authorId}")
	public ResponseEntity<String> deleteAuthor(
			@PathVariable Integer authorId)
	{
		
		String response = authorService.deleteAuthor(authorId);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<List<Author>> getAllAuthors()
	{
		List<Author> authorList = authorService.getAllAuthors();
		
		return new ResponseEntity<>(authorList,HttpStatus.OK);
	}

}
