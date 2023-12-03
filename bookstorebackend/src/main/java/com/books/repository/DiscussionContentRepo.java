package com.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.entities.DiscussionContent;

public interface DiscussionContentRepo extends JpaRepository<DiscussionContent, Integer>{

}
