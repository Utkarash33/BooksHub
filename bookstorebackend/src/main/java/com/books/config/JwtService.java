package com.books.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	
	private  String SECRET_KEY= SecurityConstants.JWT_KEY;

	
	public String extractUsername(String token) {

		return extractClaim(token, Claims::getSubject);
	}
	
	public<T> T extractClaim(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
				
	}

	public Key getSignInKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generalToken(UserDetails details)
	{
		return generalToken(new HashMap<>(), details);
	}

   public String generalToken(Map<String,Object> claims, UserDetails details)
   {
	   return Jwts.builder()
			   .setClaims(claims)
			   .setSubject(details.getUsername())
			   .setIssuedAt(new Date(System.currentTimeMillis()))
			   .setExpiration(new Date(System.currentTimeMillis() + 30000000))
			   .signWith(getSignInKey(),SignatureAlgorithm.HS256)
			   .compact();
   }

   
   public boolean isTokenValid(String token , UserDetails details)
   {
	   final String username = extractUsername(token);
	   return username.equals(details.getUsername()) && !isTokenExpired(token);
   }

private boolean isTokenExpired(String token) {
	
	return extractClaim(token, Claims::getExpiration).before(new Date());
}
}
