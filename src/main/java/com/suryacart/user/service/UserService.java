package com.suryacart.user.service;

import com.suryacart.user.model.dto.UserDTO;
import com.suryacart.user.model.entity.User;

public interface UserService {
	User findByUsername(String username);

	User registerUser(UserDTO userDTO);
}
