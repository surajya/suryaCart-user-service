package com.suryacart.user.serviceImpl;

import org.springframework.stereotype.Service;

import com.suryacart.user.mapper.ContactMapper;
import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.model.entity.Contacts;
import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.ContactRepository;
import com.suryacart.user.repository.UserRepositoryImpl;
import com.suryacart.user.service.ContactService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

	private final ContactRepository contractRepository;
	private final UserRepositoryImpl userRepository;
	private final ContactMapper contactMapper;

	@Override
	public void addContactToUser(ContactDTO contactDTO, String username) {
		User user = userRepository.getUserByUserName(username);
		Contacts contacts = new Contacts();
		contactMapper.mapContactDTOToContact(contactDTO, contacts);
		contacts.setUser(user);
		contacts.setImageId("default.png");
		user.getContacts().add(contacts);
		contractRepository.save(contacts);
	}
}
