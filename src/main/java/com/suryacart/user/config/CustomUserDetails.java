package com.suryacart.user.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.suryacart.user.model.entity.User;

public class CustomUserDetails implements UserDetails {

	User user;

	public CustomUserDetails(User user) {
		super();

		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(String.valueOf(user.getRole()));

		return List.of(simpleGrantedAuthority);

	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

}
