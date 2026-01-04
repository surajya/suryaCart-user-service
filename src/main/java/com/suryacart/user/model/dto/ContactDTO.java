package com.suryacart.user.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Nickname is required")
	private String nickname;

	@NotBlank(message = "Work is required")
	private String work;

	@Column(unique = true)
	@NotBlank(message = "Email is required")
	private String email;


	@Column(length = 1000)
	@NotBlank(message = "Description is required")
	private String description;

	@Column(length = 15)
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;

}
