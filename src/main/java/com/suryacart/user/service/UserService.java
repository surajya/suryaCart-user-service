package com.suryacart.user.service;

import com.suryacart.user.model.entity.User;

public interface UserService {
	User findByUsername(String username);

	User register(User user);
}
