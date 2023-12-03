package com.books.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.books.entities.Author;
import com.books.entities.Book;
import com.books.entities.BookReview;
import com.books.entities.User;
import com.books.exceptions.NoDataFoundException;
import com.books.repository.AuthorRepo;
import com.books.repository.BookRepo;
import com.books.repository.ReviewRatingRepo;
import com.books.repository.UserRepo;

@Service
public class BookReviewServiceImpl implements BookReviewService {
	
	
	@Autowired
	BookRepo bookRepo;
	
	@Autowired
	ReviewRatingRepo ratingRepo;
	
	@Autowired
	AuthorRepo authorRepo;
	
	@Autowired
	UserRepo userRepo;
	

	@Override
	public List<BookReview> getBookReviews(String bookId) {

      try {
           Integer id =	Integer.parseInt(bookId);
           Book book = bookRepo.findById(id).orElseThrow(() -> new NoDataFoundException("Book not found"));
           
         return book.getReviews();
           
	} catch (NumberFormatException | NoDataFoundException ex) {
		
		if(!bookRepo.findByGoogleId(bookId).isEmpty())
    	{
          Book book = bookRepo.findByGoogleId(bookId).get(0);
	      
	        
           if(book.getReviews().size()==0)
        	   {
        	   throw new NoDataFoundException("No Review avaliable");
        	   }
           else
        	   {
        		 return book.getReviews();  
        	   }
          
    	}else
    	{
    	  throw new NoDataFoundException("No Review avaliable");
    	}
	}
		
		
	}

	@Override
	public BookReview createReview(String bookId,String review, Integer rating,Integer userId) {
		
		    BookReview bookReview = new BookReview();
	        bookReview.setCreated_at(LocalDate.now());
	        bookReview.setReview(review);
	        bookReview.setRating(rating);
	        
	        User user = userRepo.findById(userId).orElseThrow(()-> new NoDataFoundException("User not found"));
		     bookReview.setUser(user);
		try {
	        Integer id = Integer.parseInt(bookId);
	        Book book = bookRepo.findById(id).orElseThrow(() -> new NoDataFoundException("Book not found"));
	        
	       
	        bookReview.setBook(book);
	        
	        book.getReviews().add(bookReview);
	        bookRepo.save(book);
	        
	        return ratingRepo.save(bookReview);
	        
	        
	    } catch (NumberFormatException | NoDataFoundException ex) {
	    	
	    	if(!bookRepo.findByGoogleId(bookId).isEmpty())
	    	{
              Book book = bookRepo.findByGoogleId(bookId).get(0);
  	        bookReview.setBook(book);
  	        
  	        book.getReviews().add(bookReview);
  	        bookRepo.save(book);
  	        
  	        return ratingRepo.save(bookReview);
              
	    	}
	    	
	        String url = "https://www.googleapis.com/books/v1/volumes/" +bookId;
	        RestTemplate restTemplate = new RestTemplate();

	        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
	                url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});

	        Map<String, Object> responseBody = responseEntity.getBody();
	        if (responseBody == null) {
	            throw new NoDataFoundException("Book not found in Google Books API");
	        }

	        Map<String, Object> volumeInfo = (Map<String, Object>) responseBody.get("volumeInfo");
	        if (volumeInfo == null) {
	            throw new NoDataFoundException("Book info not found in Google Books API response");
	        }

	        List<String> authors = (List<String>) volumeInfo.get("authors");
	        List<Author> authorList = new ArrayList<>();

	        for (String s : authors) {
	            Author author = authorRepo.findByName(s).orElseGet(() -> {
	                Author newAuthor = new Author();
	                newAuthor.setName(s);
	                return newAuthor;
	            });
	            authorList.add(author);
	        }

	        Book book = new Book();
	        book.setGoogleId(bookId);
	        
	        book.setAuthors(authorList);
	        book.setDescription((String) volumeInfo.get("description"));
	        book.setPage_count((Integer) volumeInfo.get("pageCount"));
	        book.setTitle((String) volumeInfo.get("title"));

	        Map<String, Object> images = (Map<String, Object>) volumeInfo.get("imageLinks");
	        book.setImage(images != null ? (String) images.get("thumbnail") : null);
	        book.setReviews(ratingRepo.findByBook(book));
	        
	        book.setReviews(new ArrayList<>());
	        book.getReviews().add(bookReview);
	        bookRepo.save(book);
	        return ratingRepo.save(bookReview);
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving book information: " + e.getMessage());
	    }
		
	}

	@Override
	public BookReview updateReview(Integer reviewId) {
		
	    
		return null;
	}

	@Override
	public String deleteReview(Integer reviewId) {
	    BookReview bookReview = ratingRepo.findById(reviewId)
	            .orElseThrow(() -> new NoDataFoundException("Data Not found."));

	    // Remove the association with Book
	    Book book = bookReview.getBook();
	    book.getReviews().remove(bookReview);

	    // Remove the association with User
	    User user = bookReview.getUser();
	    user.getBookReviews().remove(bookReview);

	    ratingRepo.delete(bookReview);

	    return "Review has been removed";
	}



}
