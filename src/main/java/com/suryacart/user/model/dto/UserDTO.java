package com.suryacart.user.model.dto;

import com.suryacart.user.Constant.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
	private String name;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 60, message = "Password must be between 8 and 60 characters")
	private String password;

	@NotNull(message = "Role is required")
	private Role role;

	@Size(min = 5, max = 1000, message = "Information must be between 5 and 1000 characters")
	@NotBlank(message = "Information is required")
	private String information;

}
