package com.suryacart.user.serviceImpl;

import org.springframework.stereotype.Service;

import com.suryacart.user.model.entity.Contacts;
import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.UserRepositoryImpl;
import com.suryacart.user.service.ContactService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

	private final UserRepositoryImpl userRepository;

	@Override
	public void addContactToUser(Contacts contacts, String username) {
		User user = userRepository.getUserByUserName(username);
		contacts.setUser(user);
		user.getContacts().add(contacts);
		userRepository.save(user);
	}
}
