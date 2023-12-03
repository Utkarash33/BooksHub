package com.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.entities.ReadingList;

public interface ReadingListRepo extends JpaRepository<ReadingList, Integer> {

}
