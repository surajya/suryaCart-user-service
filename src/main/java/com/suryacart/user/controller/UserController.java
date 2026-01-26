package com.suryacart.user.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.suryacart.user.helper.Message;
import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.service.ContactService;
import com.suryacart.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/userControll")
public class UserController {

	private final UserService userService;
	private final ContactService contactService;
	private final HttpSession session;

	@ModelAttribute
	public void getCommonData(Model model, Principal principal) {
		model.addAttribute("user", userService.findByUsername(principal.getName()));
	}

	@GetMapping("/index")
	public String userDashboard(Model model) {

		model.addAttribute("title", "User Dashboard");
		return "/normal/User_DashBoard";
	}

	@GetMapping("/addContact")
	public String openAddContactForm(Model model) {

		model.addAttribute("title", "Add Contact");
		model.addAttribute("contactDTO", new ContactDTO());
		session.removeAttribute("message");
		return "/normal/add_contact_form";
	}

	@PostMapping("/process-contact")
	public String processContact(@Valid @ModelAttribute("contactDTO") ContactDTO contactDTO,
			@RequestParam("image") MultipartFile imageFile, Principal principal, Model model) {

		try {
			contactService.addContactToUser(contactDTO, imageFile, principal.getName());
			session.setAttribute("message", new Message("New Contact Add Successfully!!", "alert-success"));
			model.addAttribute("contactDTO", new ContactDTO());
		} catch (Exception e) {
			session.setAttribute("message", new Message("Error: " + e.getMessage(), "alert-danger"));
			model.addAttribute("contactDTO", contactDTO);
		}
		model.addAttribute("title", "Add Contact");
		return "/normal/add_contact_form";
	}

	//Show all contacts handler
	@GetMapping("/show-contacts")
	public String showContacts(Model model, Principal principal) {
		model.addAttribute("title", "Show Contacts");
		model.addAttribute("contacts", contactService.getContactsByUser(principal.getName()));
		return "/normal/show_contacts";
	}

	//Handle image request
	@GetMapping("/contact/image/{imageId}")
	public ResponseEntity<byte[]> getContactImage(@PathVariable String imageId) {

		byte[] imageBytes = loadImage(imageId);

		if (imageBytes == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok()
				.contentType(MediaType.ALL) // or PNG
				.body(imageBytes);
	}

	private byte[] loadImage(String imageId) {
		try {
			Path path = Paths.get("/static/img/" + imageId);
			return Files.readAllBytes(path);
		} catch (IOException e) {
			return null;
		}
	}

}
