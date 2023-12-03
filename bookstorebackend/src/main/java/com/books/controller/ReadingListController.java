package com.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.books.entities.ReadingList;
import com.books.services.ReadingListService;

@RestController
@RequestMapping("/reading-lists")
public class ReadingListController {

    @Autowired
    private ReadingListService readingListService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ReadingList>> getUserReadingList(@PathVariable Integer userId) {
       
    	List<ReadingList> readingList = readingListService.getUserReadingList(userId);
        return new ResponseEntity<>(readingList, HttpStatus.OK);
    }

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<ReadingList> addToReadingList(@PathVariable Integer userId, @PathVariable String bookId) {
        
            ReadingList list= readingListService.addBookToList(userId,bookId);
       
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @DeleteMapping("/{readingListId}")
    public ResponseEntity<String> removeFromReadingList(@PathVariable Integer readingListId) {
        
    	
    	String message = readingListService.removeItemFromList(readingListId);
    	 System.out.println(message);
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
 
}
