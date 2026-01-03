package com.suryacart.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.UserRepositoryImpl;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepositoryImpl userRepositoryImpl;

	public CustomUserDetailsService() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User userName = userRepositoryImpl.getUserByUserName(username);
		if (userName == null) {
			throw new UsernameNotFoundException("USER IS NOT FOUND !!");
		}

		return new CustomUserDetails(userName);
	}

}
