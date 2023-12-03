package com.books.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	 private ObjectMapper objectMapper;

	    @Autowired
	    public void setObjectMapper(ObjectMapper objectMapper) {
	        this.objectMapper = objectMapper;
	    }
	    @Override
	    public void commence(HttpServletRequest request, HttpServletResponse response,
	                         AuthenticationException authException) throws IOException, ServletException {
	        response.setContentType("application/json;charset=UTF-8");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Unauthorized");
	        errorResponse.put("message", authException.getMessage());

	        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	    }

}
