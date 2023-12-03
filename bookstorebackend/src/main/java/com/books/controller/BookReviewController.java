package com.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.entities.BookReview;
import com.books.services.BookReviewService;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins ="*",allowedHeaders = "*")
public class BookReviewController {

	
	
	@Autowired
	private BookReviewService bookReviewService;
	
	
	@GetMapping("/{bookId}")
	public ResponseEntity<List<BookReview>> getBookReviews(
			@PathVariable String bookId	
			)
	{
		
		List<BookReview> reviewList = bookReviewService.getBookReviews(bookId);
		
		return new ResponseEntity<>(reviewList,HttpStatus.OK);
	}
	
	
	@PostMapping("/{bookId}")
	public ResponseEntity<BookReview> createBookReviews(
			@PathVariable String bookId	,
			@RequestParam String review,
			@RequestParam Integer rating,
			@RequestParam Integer userId
			)
	{
		
		BookReview bookReview = bookReviewService.createReview(bookId,review,rating,userId);
		
		return new ResponseEntity<>(bookReview,HttpStatus.OK);
	}
	
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<BookReview> updateBookReview(
			@PathVariable Integer reviewId
			)
	{
		BookReview bookReview = bookReviewService.updateReview(reviewId);
		
		return new ResponseEntity<>(bookReview,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteBookReviw(
			@PathVariable Integer reviewId
			)
	{
		
       String  bookReview = bookReviewService.deleteReview(reviewId);
		
		return new ResponseEntity<>(bookReview,HttpStatus.OK);
	}
	
	
	
}
