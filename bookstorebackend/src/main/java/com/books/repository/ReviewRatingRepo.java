package com.books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.books.entities.Book;
import com.books.entities.BookReview;


public interface ReviewRatingRepo extends JpaRepository<BookReview, Integer>{

	
	@Query("SELECT br FROM BookReview br WHERE br.book = :book")
    List<BookReview> findByBook(@Param("book") Book book);
}
