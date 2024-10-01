package com.dawan.gquizz.services;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new  UsernameNotFoundException("Aucun utilisateur ne correspond à : " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), 
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
	}
} 
