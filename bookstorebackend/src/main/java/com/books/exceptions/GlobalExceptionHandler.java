//package com.books.exceptions;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
//        return ResponseEntity.status(401).body("Authentication failed: " + ex.getMessage());
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
//        return ResponseEntity.status(401).body("Bad credentials: " + ex.getMessage());
//    }
//
//
//   
//}
