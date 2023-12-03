package com.books.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.books.entities.User;
import com.books.exceptions.NoDataFoundException;
import com.books.repository.UserRepo;
@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserRepo repo;
	
	
	public boolean isAdmin(String email)
	{
		if(email.contains("admin"))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
			Optional<User> opt = repo.findByUsername(username);
			
			User user = opt.orElseThrow(()-> new NoDataFoundException("Invalid username."));
			
			
			List<GrantedAuthority> authorites = new ArrayList<>();
			SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE"+user.getRole().toUpperCase());
			
			authorites.add(grantedAuthority);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorites);
		
		
	}

}
