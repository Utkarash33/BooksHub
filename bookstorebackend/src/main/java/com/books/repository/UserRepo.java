package com.books.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);

}
