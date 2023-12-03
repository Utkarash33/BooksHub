package com.books.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.entities.Book;


public interface BookRepo extends JpaRepository<Book, Integer>{
	
	   
       List<Book> findByGoogleId(String googleId);
        Optional<Book> findByTitle(String title);

}
