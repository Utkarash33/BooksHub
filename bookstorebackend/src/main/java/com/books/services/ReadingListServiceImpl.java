package com.books.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.books.entities.Author;
import com.books.entities.Book;
import com.books.entities.BookReview;
import com.books.entities.ReadingList;
import com.books.entities.User;
import com.books.exceptions.DuplicationDataException;
import com.books.exceptions.NoDataFoundException;
import com.books.repository.AuthorRepo;
import com.books.repository.BookRepo;
import com.books.repository.ReadingListRepo;
import com.books.repository.ReviewRatingRepo;
import com.books.repository.UserRepo;

@Service
public class ReadingListServiceImpl implements ReadingListService{

	@Autowired
	ReadingListRepo listRepo;
	
	@Autowired
	UserRepo repo;
 
	@Autowired
	BookRepo bookRepo;
	
	@Autowired
	ReviewRatingRepo ratingRepo;
	
	@Autowired
	AuthorRepo authorRepo;
	
	
	@Override
	public List<ReadingList> getUserReadingList(Integer userId) {
		
		
		List<ReadingList> readingList = listRepo.findAll()
				                         .stream()
				                         .filter(u->u.getUser().getId()==userId)
				                         .toList();
		return readingList;
	}

	@Override
	public ReadingList addBookToList(Integer userId, String bookId) {
		
		
		User user = repo.findById(userId).orElseThrow(()-> new RuntimeException("user not found."));
		
		
		
		try {
			 
			
		Book book =	bookRepo.findById(Integer.parseInt(bookId)).orElseThrow(()-> new RuntimeException("no book in database."));
		
		if(user.getReadingLists().contains(book))
		{
			throw new DuplicationDataException("Book already present in the reading list.");
		}
		
		ReadingList readingList = new ReadingList();
		
		readingList.setAddedAt(LocalDateTime.now());
		readingList.setBook(book);
		readingList.setUser(user);
		
		return listRepo.save(readingList);

		} catch (Exception e) {
			
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

	        for (String authorName : authors) {
	            Optional<Author> existingAuthor = authorRepo.findByName(authorName);

	            if (existingAuthor.isPresent()) {
	                // If the author already exists, reuse the existing one
	                authorList.add(existingAuthor.get());
	            } else {
	                // If the author doesn't exist, create a new one and save it
	                Author newAuthor = new Author();
	                newAuthor.setName(authorName);
	                authorRepo.save(newAuthor);
	                authorList.add(newAuthor);
	            }
	        } 
	        
	       
	        Book book = new Book();
	        book.setGoogleId(bookId);
	        
	        
	       
	       String truncatedDescription = (String) volumeInfo.get("description");

	    	book.setDescription(truncatedDescription);
	        book.setPage_count((Integer) volumeInfo.get("pageCount"));
	        book.setTitle((String) volumeInfo.get("title"));
	        book.setPublished_date((String) volumeInfo.get("publishedDate"));
	        Map<String, Object> images = (Map<String, Object>) volumeInfo.get("imageLinks");
	        book.setImage(images != null ? (String) images.get("thumbnail") : null);
	        
	       
	      if(!bookRepo.findByTitle(book.getTitle()).isPresent())
	      {
	    	  bookRepo.save(book); 
	      }
	      else
	      {
	    	  book= bookRepo.findByTitle(book.getTitle()).get();
	      }
	      List<BookReview> bookReviews = ratingRepo.findByBook(book);
	     book.setReviews(bookReviews==null ? new ArrayList<>():bookReviews);
	     book.setAuthors(authorList);
	       
	     if(user.getReadingLists().contains(book))
			{
				throw new DuplicationDataException("Book already present in the reading list.");
			}
			
	       ReadingList readingList = new ReadingList();
			
			readingList.setAddedAt(LocalDateTime.now());
			readingList.setBook(book);
			readingList.setUser(user);
			
			
			
			return listRepo.save(readingList);
			
			
			
		}
	}

	@Override
	public String removeItemFromList(Integer readingListId) {


		   ReadingList list= listRepo.findById(readingListId).orElseThrow(()-> new NoDataFoundException("Readinglist item no found."));
		   
		   listRepo.delete(list);
		   
		return "Book has been removed";
	}

}
