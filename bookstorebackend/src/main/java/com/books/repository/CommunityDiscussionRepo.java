package com.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.entities.CommunityDiscussion;

public interface CommunityDiscussionRepo extends JpaRepository<CommunityDiscussion, Integer> {

}
