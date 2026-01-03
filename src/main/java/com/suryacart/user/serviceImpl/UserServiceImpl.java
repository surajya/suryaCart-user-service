package com.suryacart.user.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suryacart.user.mapper.UserMapper;
import com.suryacart.user.model.dto.UserDTO;
import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.UserRepositoryImpl;
import com.suryacart.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepositoryImpl userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Override
	public User findByUsername(String username) {
		return userRepository.getUserByUserName(username);
	}

	@Override
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		userMapper.mapUserDTOToUser(userDTO, user);
		user.setImage("This is image");
		user.setEnabled(true);
		user.setRole(userDTO.getRole());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}
