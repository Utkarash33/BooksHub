package com.books.services;

import java.util.List;

import com.books.entities.ReadingList;

public interface ReadingListService {

	List<ReadingList> getUserReadingList(Integer userId);

	ReadingList addBookToList(Integer userId, String bookId);

	String removeItemFromList(Integer readingListId);

}
