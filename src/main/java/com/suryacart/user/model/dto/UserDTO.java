package com.suryacart.user.model.dto;

import com.suryacart.user.Constant.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;

	private String name;

	private String email;

	private String password;

	private String image;

	private Role role;

	private boolean enabled;

	private String information;

}
