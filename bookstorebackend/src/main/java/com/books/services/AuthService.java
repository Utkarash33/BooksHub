package com.books.services;

import com.books.DTO.LoginRequest;
import com.books.entities.User;

public interface AuthService {

	String registerUser(User user);

	String loginUser(LoginRequest loginRequest);

	User loginUser(String User);

}
