package com.books.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins ="*",allowedHeaders = "*")
public class testController {

	
	
	@PostMapping("/hello")
	public String postMethodName() {
		return "hello world post";
	}
	
	@GetMapping("/hello2")
	public String getMethodName() {
		return "hello world get";
	}

}
