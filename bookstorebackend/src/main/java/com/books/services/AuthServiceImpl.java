package com.books.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.books.DTO.LoginRequest;
import com.books.config.JwtService;
import com.books.entities.Author;
import com.books.entities.User;
import com.books.exceptions.DuplicationDataException;
import com.books.repository.AuthorRepo;
import com.books.repository.UserRepo;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public String registerUser(User user) {
     
        if (repo.findByUsername(user.getUsername()).isPresent()) {
           throw new DuplicationDataException("User already present");
        }

      
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(user.getRole().toUpperCase());

      
        repo.save(user);
        if(user.getRole().equals("AUTHOR"))
        	
        {
        	Author author = new Author();
        	author.setName(user.getName());
        	authorRepo.save(author);
        }

        return "User registered successfully.";
    }

    @Override
    public User loginUser(String email) {
       
        User user = repo.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

       
        
      
        return user;
    }

	@Override
	public String loginUser(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}