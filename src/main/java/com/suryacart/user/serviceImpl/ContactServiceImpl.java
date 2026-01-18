package com.suryacart.user.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.suryacart.user.mapper.ContactMapper;
import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.model.entity.Contacts;
import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.ContactRepository;
import com.suryacart.user.repository.UserRepositoryImpl;
import com.suryacart.user.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

	private final ContactRepository contractRepository;
	private final UserRepositoryImpl userRepository;
	private final ContactMapper contactMapper;

	@Override
	public void addContactToUser(ContactDTO contactDTO, MultipartFile imageFile, String username) {
		User user = userRepository.getUserByUserName(username);
		Contacts contacts = new Contacts();
		contactMapper.mapContactDTOToContact(contactDTO, contacts);
		processContactAndAddImange(user, imageFile, contacts);
		contractRepository.save(contacts);
	}

	private void processContactAndAddImange(User user, MultipartFile imageFile, Contacts contacts) {
		contacts.setUser(user);
		contacts.setImageId(contacts.getName() + "-" + imageFile.getOriginalFilename());
		contacts.setCreatedBy(user.getName());
		contacts.setUpdatedBy(user.getName());
		user.getContacts().add(contacts);

		// Here, you would typically handle the image file saving process.
		try {
			if (imageFile.isEmpty()) {
				log.info("Image file is empty, skipping image save.");
			}
			File pathResource = new ClassPathResource("/static/img").getFile();
			Path path = Paths.get(pathResource.getAbsolutePath() + File.separator + contacts.getImageId());
			Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			log.info("Image file saved successfully at: " + path.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
