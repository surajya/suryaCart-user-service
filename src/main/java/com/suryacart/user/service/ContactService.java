package com.suryacart.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.suryacart.user.model.dto.ContactDTO;

public interface ContactService {

	void addContactToUser(ContactDTO contactDTO, MultipartFile imageFile, String username);
}
