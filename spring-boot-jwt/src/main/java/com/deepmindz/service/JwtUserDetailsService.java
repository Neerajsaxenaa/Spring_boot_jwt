package com.deepmindz.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("neeraj".equals(username)) {
			return new User("neeraj", "$2a$10$wkuvKBQme0EhqhS4NBqgG.Qq3wJGZm7IuZ9WrbinzxZpyttp0KPi.",
					new ArrayList<>());
		} else if ("akash".equals(username)) {
			return new User("akash", "$2a$10$WBO9oiqUPLylCxZkllWiCeQShRhOvC/Siox150BvFbNfW5IpanL6i", new ArrayList<>());

		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}