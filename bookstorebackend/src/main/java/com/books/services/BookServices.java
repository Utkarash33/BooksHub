package com.books.services;

import java.util.List;

import com.books.DTO.BooksDTO;
import com.books.entities.Book;

public interface BookServices {

	
	
	List<BooksDTO> getAllBooks(int page,int limit);

	Book createBook(BooksDTO bookdto);

	String deleteBook(String bookId);

	Book updateBook(BooksDTO bookdto,String bookId);

	Book getBookById(String bookId);
	
	
}
