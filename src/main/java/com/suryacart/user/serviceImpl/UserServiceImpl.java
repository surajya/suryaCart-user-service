package com.suryacart.user.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suryacart.user.Constant.Role;
import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.UserRepositoryImpl;
import com.suryacart.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepositoryImpl userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByUsername(String username) {
		return userRepository.getUserByUserName(username);
	}

	@Override
	public User register(User user) {
		user.setImage("This is image");
		user.setEnabled(true);
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}
