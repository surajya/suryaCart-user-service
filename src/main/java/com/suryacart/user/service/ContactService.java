package com.suryacart.user.service;

import com.suryacart.user.model.dto.ContactDTO;

public interface ContactService {

	void addContactToUser(ContactDTO contactDTO, String username);
}
