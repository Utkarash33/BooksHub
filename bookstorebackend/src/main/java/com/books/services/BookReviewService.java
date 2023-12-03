package com.books.services;

import java.util.List;

import com.books.entities.BookReview;

public interface BookReviewService {

	List<BookReview> getBookReviews(String bookId);


	BookReview updateReview(Integer reviewId);

	String deleteReview(Integer reviewId);

	BookReview createReview(String bookId, String review, Integer rating,Integer userId);

}
