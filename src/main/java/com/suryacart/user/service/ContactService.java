package com.suryacart.user.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.model.entity.Contacts;

public interface ContactService {

	void addContactToUser(ContactDTO contactDTO, MultipartFile imageFile, String username)
			throws IllegalArgumentException, IOException;

	List<Contacts> getContactsByUser(String username);
}
