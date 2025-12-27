package com.suryacart.user.model.dto;

import com.suryacart.user.Constant.Role;

public class UserDTO {
	public record UserRegisterRequest(String name, String email, String password) {
	}

	public record UserResponse(Long id, String name, String email, Role role, boolean enabled, String image,
			String information) {
	}

}
