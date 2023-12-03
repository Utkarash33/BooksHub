package com.books.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.books.DTO.LoginRequest;
import com.books.DTO.RegistrationDTO;
import com.books.entities.User;
import com.books.services.AuthService;
import com.books.services.AuthorService;

import ch.qos.logback.core.util.SystemInfo;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private AuthService authService;
	
	
	
	@GetMapping("/{email}")
	public ResponseEntity<User> getUserById (@PathVariable String email)
	{
		
		
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	@PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDTO requestDTO) {
      
		
		User user = new User();
		
		user.setName(requestDTO.getName());
		user.setUsername(requestDTO.getEmail());
		user.setPassword(requestDTO.getPassword());
		user.setRole(requestDTO.getRole());
		
		
	  
		String response= authService.registerUser(user);
		
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<User> loginUser(Authentication auth) {
        
    	System.out.println(auth.getName());
    	User response = authService.loginUser(auth.getName());
    	
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
