package com.suryacart.user.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ContactDTO {
	public record ContactRequest(String name, String nickname, String work, String email, String phoneNumber,
			String description, MultipartFile image) {
	}

	public record ContactResponse(Long id, String name, String nickname, String work, String email, String phoneNumber,
			String description) {
	}

}
