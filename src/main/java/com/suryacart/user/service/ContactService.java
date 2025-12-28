package com.suryacart.user.service;

import com.suryacart.user.model.entity.Contacts;

public interface ContactService {
	void addContactToUser(Contacts contacts, String username);
}
