package com.example.demo.service.user;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	private static final String USER_NAME = "admin";
    private static final String PASSWORD = "admin";
    
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
    String encodedPassword = encoder.encode(PASSWORD);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 if (USER_NAME.equals(username)){
	            return new User("admin", encodedPassword, new ArrayList<>());
	        }
	 
	        throw new UsernameNotFoundException(username);		
	}

}

