package com.books.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());
            String jwt = Jwts.builder()
            		.setIssuer("Admin")
            		.setSubject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+ 30000000))
                    .signWith(key).compact();
                       
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
            
            Cookie jwtCookie = new Cookie(SecurityConstants.JWT_HEADER, jwt); // Replace jwt with your actual JWT token
            jwtCookie.setMaxAge((int) (System.currentTimeMillis() + 30*24*60*60*1000)); // Set cookie expiration time
            jwtCookie.setPath("/");
            
            response.addCookie(jwtCookie);
            
            System.out.println(jwt);
           
        }
        filterChain.doFilter(request, response);	
	}
	
	  private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
	        
	    	Set<String> authoritiesSet = new HashSet<>();
	        
	        for (GrantedAuthority authority : collection) {
	            authoritiesSet.add(authority.getAuthority());
	        }
	        return String.join(",", authoritiesSet);
	   
	    
	    }
		
	//this make sure that this filter will execute only for first time when client call the api /signIn at first time
		@Override
		protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
	        return (request.getServletPath().equals("/auth/register"));	
		}

}
