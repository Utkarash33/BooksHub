package com.books.DTO;

import lombok.Data;

@Data
public class LoginRequest {

	
	String email;
	String password;
	String role;
}
