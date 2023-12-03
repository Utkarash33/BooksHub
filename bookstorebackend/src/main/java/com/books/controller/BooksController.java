package com.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.DTO.BooksDTO;
import com.books.entities.Book;
import com.books.services.BookServices;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins ="*",allowedHeaders = "*")
public class BooksController {

	
	@Autowired
	private BookServices bookServices;  
	
	@GetMapping("")
	public ResponseEntity<List<BooksDTO>> getAllBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int limit)
	{
		
		List<BooksDTO> allBooks = bookServices.getAllBooks(page,limit);
		
		return new ResponseEntity<>(allBooks,HttpStatus.OK);
		
	}
	
	
   @GetMapping("/{bookId}")
   public ResponseEntity<Book> getBookById(@PathVariable String bookId)
   {
	   
	   Book book = bookServices.getBookById(bookId);
	   
	   return new ResponseEntity<>(book,HttpStatus.OK);
   }
	
    
	@PostMapping("/create")
	public ResponseEntity<Book> createBook(@RequestBody BooksDTO bookdto)
	{
		
		System.out.println(bookdto);
	   Book book =	bookServices.createBook(bookdto);
		
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{bookId}")
	public ResponseEntity<Book> updateBook(@RequestBody BooksDTO bookdto,@PathVariable String bookId)
	{
		 Book book =	bookServices.updateBook(bookdto,bookId);
			
			return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable String bookId)
	{
       String response = bookServices.deleteBook(bookId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	
}
