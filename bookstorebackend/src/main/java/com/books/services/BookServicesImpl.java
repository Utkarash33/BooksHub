package com.books.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.books.DTO.BooksDTO;
import com.books.entities.Author;
import com.books.entities.Book;
import com.books.entities.BookReview;
import com.books.entities.ReadingList;
import com.books.exceptions.NoDataFoundException;
import com.books.repository.AuthorRepo;
import com.books.repository.BookRepo;
import com.books.repository.ReviewRatingRepo;

@Service
public class BookServicesImpl implements BookServices{

	
	private String apiUrl="https://www.googleapis.com/books/v1/volumes?q=*";
	
	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private AuthorRepo authorRepo;
	
	@Autowired
	private ReviewRatingRepo ratingRepo;
	
	@Override
	public List<BooksDTO> getAllBooks(int page,int limit)
	{
		
		
		List<BooksDTO> books = new ArrayList<>();
		
		//get books from the database
		 if(page==1)
		 {
			 
			 List<Book> booksFromDataBase= bookRepo.findAll();
				if(booksFromDataBase.size()!=0)
				for(Book book: booksFromDataBase)
				{
					BooksDTO dto = new BooksDTO();
					
					 dto.setTitle(book.getTitle());
					 dto.setDescription(book.getDescription());
					 dto.setPublishedDate(book.getPublished_date());
					 dto.setPageCount(book.getPage_count());
					 dto.setId(book.getId().toString());
					  dto.setImageUrl(book.getImage());
					 List<String> authorList = new ArrayList<>();
					 
					 if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
						    authorList.add(book.getAuthors().get(0).getName());
						}
					 dto.setAuthors(authorList);
					 
					
					 books.add(dto);
					 
				}
				
		 }
		
		 limit-=books.size();
		//get books from the api
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map<String,Object>> responseEntity = restTemplate.exchange(
				apiUrl+"startIndex="+page*25+"&maxResults="+limit,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Map<String,Object>>() {});
		
		Map<String,Object> responseBody = responseEntity.getBody();
		List<Map<String, Object>> items = (List<Map<String, Object>>) responseBody.get("items");
		
		//convert books from api to booksDTO
		for(Map<String,Object> item : items)
		{
			 Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo"); 
			 BooksDTO dto = new BooksDTO(); 
			 dto.setTitle((String) volumeInfo.get("title"));
			 dto.setAuthors((List<String>) volumeInfo.get("authors"));
			 dto.setDescription((String) volumeInfo.get("description"));
			 dto.setPublishedDate((String) volumeInfo.get("publishedDate"));
			 dto.setPageCount((Integer) volumeInfo.get("pageCount"));
			 dto.setId((String)item.get("id")); 
			 Map<String,Object> images =(Map<String,Object>) volumeInfo.get("imageLinks");
			 dto.setImageUrl((String)images.get("thumbnail"));
			 books.add(dto);
		}
		
		
		
		
		return books;
	}

	@Override
	public Book createBook(BooksDTO bookdto) {
	
		Book book = new Book();
		
		book.setDescription(bookdto.getDescription());
		book.setTitle(bookdto.getTitle());
		book.setPage_count(bookdto.getPageCount());
		book.setPublished_date(bookdto.getPublishedDate());
		
		List<Author> authorList = new ArrayList();
		
		authorList.add(authorRepo.findByName(bookdto.getAuthors().get(0))
				   .orElseThrow(
						   ()->new NoDataFoundException("Author not found")
						   ));
		book.setAuthors(authorList);
		
		
		
		List<ReadingList> readingList = new ArrayList<>();
		
		book.setReadingLists(readingList);
		
		return bookRepo.save(book);
	}

	@Override
	public String deleteBook(String bookId) {
		
		Book book = bookRepo.findById(Integer.parseInt(bookId)).orElseThrow(
				()-> new NoDataFoundException("No Book avaliable with the id:-"+bookId));
		
		
		
		bookRepo.delete(book);
		
		
		return "Book has been removed.";
	}

	@Override
	public Book updateBook(BooksDTO bookdto, String bookId) {
		Book book = bookRepo.findById(Integer.parseInt(bookId)).orElseThrow(
				()-> new NoDataFoundException("No Book avaliable with the id:-"+bookId));
		book.setDescription(bookdto.getDescription());
		book.setTitle(bookdto.getTitle());
		book.setPage_count(bookdto.getPageCount());
		book.setPublished_date(bookdto.getPublishedDate());
		book.setImage(bookdto.getImageUrl());
		return bookRepo.save(book);
	}

	@Override
	public Book getBookById(String bookId) {
    try {
        Integer id = Integer.parseInt(bookId);
       return bookRepo.findById(id).orElseThrow(() -> new NoDataFoundException("Book not found"));

    } catch (NumberFormatException | NoDataFoundException ex) {
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
    
     return book;
    } catch (Exception e) {
        throw new RuntimeException("Error retrieving book information: " + e.getMessage());
    }
}


}
