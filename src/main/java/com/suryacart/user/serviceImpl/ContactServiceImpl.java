package com.suryacart.user.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public void addContactToUser(ContactDTO contactDTO, MultipartFile imageFile, String username)
			throws IllegalArgumentException, IOException {

		if (imageFile == null || imageFile.isEmpty()) {
			throw new IllegalArgumentException("Image file is required for contact: " + contactDTO.getName());
		}
		User user = userRepository.getUserByUserName(username);
		Contacts contacts = new Contacts();
		contactMapper.mapContactDTOToContact(contactDTO, contacts);
		contacts.setDescription(contactDTO.getDescription().replaceAll("<p>", "").replaceAll("</p>", ""));
		processContactAndAddImange(user, imageFile, contacts);
		contractRepository.save(contacts);
	}

	private void processContactAndAddImange(User user, MultipartFile imageFile, Contacts contacts) throws IOException {
		contacts.setUser(user);
		contacts.setCreatedBy(user.getName());
		contacts.setUpdatedBy(user.getName());
		user.getContacts().add(contacts);

		// Here, you would typically handle the image file saving process.

		if (imageFile != null && !imageFile.isEmpty()) {
			contacts.setImageId(contacts.getName() + "-" + imageFile.getOriginalFilename());
			File pathResource = new ClassPathResource("/static/img").getFile();
			Path path = Paths.get(pathResource.getAbsolutePath() + File.separator + contacts.getImageId());
			Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			log.info("Image file saved successfully at: " + path.toString());
		}

	}

	public Page<Contacts> getContactsByUser(String username, Pageable pageable) {

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		User user = userRepository.getUserByUserName(username);
		log.info("Fetching contacts for user: {} ", user.getName());
		List<Contacts> contactsList = contractRepository.findByUserId(user.getId());

		List<Contacts> list;

		if (contactsList.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, contactsList.size());
			list = contactsList.subList(startItem, toIndex);
		}

		Page<Contacts> bookPage =
				new PageImpl<Contacts>(list, PageRequest.of(currentPage, pageSize), contactsList.size());

		return bookPage;
	}

	@Override
	public Contacts getContactByEmail(String email) {

		return contractRepository.findByEmail(email);
	}

	@Override
	public void updateContact(ContactDTO contactDTO, MultipartFile imageFile, String username)
			throws IllegalArgumentException, IOException {
		Contacts existingContact = contractRepository.findByEmail(contactDTO.getEmail());
		if (existingContact == null) {
			throw new IllegalArgumentException("Contact with email " + contactDTO.getEmail() + " not found.");
		}
		User user = userRepository.getUserByUserName(username);
		contactMapper.mapContactDTOToContact(contactDTO, existingContact);
		existingContact.setDescription(contactDTO.getDescription().replaceAll("<p>", "").replaceAll("</p>", ""));
		processContactAndAddImange(user, imageFile, existingContact);
		contractRepository.save(existingContact);
	}
}
