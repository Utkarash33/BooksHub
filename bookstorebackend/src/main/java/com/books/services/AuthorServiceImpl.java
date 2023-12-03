package com.books.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.books.entities.Author;
import com.books.entities.Book;
import com.books.exceptions.DuplicationDataException;
import com.books.exceptions.NoDataFoundException;
import com.books.repository.AuthorRepo;

@Service
public class AuthorServiceImpl implements AuthorService{

	
	private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1";
	
//	private final RestTemplate restTemplate;

    @Autowired
    private AuthorRepo authorRepo;
//
//    @Autowired
//    public AuthorServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//	
	@Override
	public Author createAuthor(String name, String bio) {
		
		Author author = new Author();
		
       if(authorRepo.findByName(name).isPresent())
       {
    	   throw new DuplicationDataException("Data is already present");
       }
       
       author.setBio(bio);
       author.setName(name);
       List<Book> bookList = new ArrayList();
       author.setBooks(bookList);
		
		return authorRepo.save(author);
	}

	@Override
	public Author updateAuthor(String bio, Integer authorId) {
		
	
		Author author = authorRepo.findById(authorId)
				.orElseThrow(()->
	       {
	    	   throw new NoDataFoundException("Data is already present");
	       });
	       author.setBio(bio);
			return authorRepo.save(author);
	}

	@Override
	public String deleteAuthor(Integer authorId) {
		Author author = authorRepo.findById(authorId)
				.orElseThrow(()->
	       {
	    	   throw new NoDataFoundException("Data is already present");
	       });
		 authorRepo.delete(author);
		 
		 return "Author has been removed";
	}

	@Override
	public List<Author> getAllAuthors() {
		// TODO Auto-generated method stub
		return null;
	}

	
//	    public List<Author> getAllAuthors() {
//	        List<Author> authorsList = authorRepo.findAll();
//
//	        if (authorsList.size() == 0) {
//	            String defaultAuthorName = "J.K. Rowling";
//	            List<String> googleApiAuthors = getAuthorsFromGoogleBooksAPI(defaultAuthorName);
//
//	            googleApiAuthors.forEach(authorName -> {
//	                Author author = new Author();
//	                author.setName(authorName);
//	                authorRepo.save(author);
//	            });
//
//	            authorsList = authorRepo.findAll();
//	        }
//
//	        return authorsList;
//	    }
//
//	    private List<String> getAuthorsFromGoogleBooksAPI(String authorName) {
//	        String apiUrl = GOOGLE_BOOKS_API_URL + "/volumes";
//	        try {
//	            ResponseEntity<Map<String, Object>> response = restTemplate.getForEntity(apiUrl, Map.class, buildParams(authorName));
//	            return extractAuthors(response.getBody());
//	        } catch (RestClientException e) {
//	            e.printStackTrace();
//	            return Collections.emptyList();
//	        }
//	    }
//
//	    private List<String> extractAuthors(Map<String, Object> response) {
//	        List<String> authors = new ArrayList<>();
//	        if (response != null && response.containsKey("items")) {
//	            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
//	            for (Map<String, Object> item : items) {
//	                Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");
//	                if (volumeInfo != null && volumeInfo.containsKey("authors")) {
//	                    List<String> authorsList = (List<String>) volumeInfo.get("authors");
//	                    authors.addAll(authorsList);
//	                }
//	            }
//	        }
//	        return authors;
//	    }
//
//	    private Map<String, String> buildParams(String authorName) {
//	        Map<String, String> params = new HashMap<>();
//	        params.put("q", "inauthor:" + authorName);
//	        return params;
//	    }
//	
//	
	
	
}
