package com.example.demo.service.user;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.user.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	private static final String USER_NAME = "admin";
	private static final String PASSWORD = "phatlambl";

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	String encodedPassword = encoder.encode(PASSWORD);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.example.demo.model.user.User user = userRepo.findUserById(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(user.getId(), user.getPassword(), new ArrayList<>());
	}

}
