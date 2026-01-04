package com.suryacart.user.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.suryacart.user.model.dto.UserDTO;
import com.suryacart.user.model.entity.User;

@Component
public class UserMapper {

	private ModelMapper modelMapper;

	public void mapUserDTOToUser(UserDTO userDTO, User user) {
		modelMapper = new ModelMapper();
		modelMapper.map(userDTO, user);
	}

	public UserDTO mapUserToUserDTO(User user) {
		modelMapper = new ModelMapper();
		return modelMapper.map(user, UserDTO.class);
	}
}
