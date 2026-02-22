package com.suryacart.user.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.model.entity.Contacts;

public interface ContactService {

	void addContactToUser(ContactDTO contactDTO, MultipartFile imageFile, String username)
			throws IllegalArgumentException, IOException;

	Page<Contacts> getContactsByUser(String username, Pageable pageable);

	Contacts getContactByEmail(String email);

	void updateContact(ContactDTO contactDTO, MultipartFile imageFile, String username)
			throws IllegalArgumentException, IOException;

}
