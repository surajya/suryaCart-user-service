package com.suryacart.user.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.suryacart.user.mapper.ContactMapper;
import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.model.entity.Contacts;
import com.suryacart.user.service.ContactService;
import com.suryacart.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/userControll")
@Slf4j
public class UserController {

	private final UserService userService;
	private final ContactService contactService;
	private final HttpSession session;
	private final ContactMapper contactMapper;

	@ModelAttribute
	public void getCommonData(Model model, Principal principal) {
		model.addAttribute("user", userService.findByUsername(principal.getName()));
	}

	@GetMapping("/index")
	public String userDashboard(Model model) {

		model.addAttribute("title", "User Dashboard");
		return "/normal/User_DashBoard";
	}

	//Open add contact form handler
	@GetMapping("/addContact")
	public String openAddContactForm(Model model) {

		model.addAttribute("title", "Add Contact");
		model.addAttribute("contactDTO", new ContactDTO());
		session.removeAttribute("message");
		return "/normal/add_contact_form";
	}

	//Process add contact form handler
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

	//Show all contacts handler with help of pagination
	@GetMapping("/show-contacts")
	public String showContacts(Model model, Principal principal, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		model.addAttribute("title", "Show Contacts");
		Page<Contacts> contactPage =
				contactService.getContactsByUser(principal.getName(), PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("contactPage", contactPage);
		int totalPages = contactPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}


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

	//Open contact Profile details
	@GetMapping("/contact/{email}")
	public String showContactDetails(@PathVariable String email, Model model, Principal principal) {
		Contacts contact = contactService.getContactByEmail(email);
		if (contact == null) {
			session.setAttribute("message", new Message("Contact not found or you don't have access!", "alert-danger"));
			return "redirect:/userControll/show-contacts";
		}
		model.addAttribute("contact", contact);
		model.addAttribute("title", "Contact Details");
		return "/normal/ViewContactProfile";
	}

	//Open update contact form
	@GetMapping("/contact/open-update/{email}")
	public String openUpdateContactForm(@PathVariable String email, Model model, Principal principal) {
		Contacts contact = contactService.getContactByEmail(email);
		if (contact == null) {
			session.setAttribute("message", new Message("Contact not found or you don't have access!", "alert-danger"));
			return "redirect:/userControll/show-contacts";
		}
		model.addAttribute("contact", contact);
		model.addAttribute("title", "Update Contact");
		return "/normal/edit_contact";
	}

	//Handle update contact form request
	@PostMapping("/contact/update-contact")
	public String updateContactDetails(@Valid @ModelAttribute("contactDTO") ContactDTO contactDTO,
			@RequestParam("image") MultipartFile imageFile, Principal principal, Model model) {
		Contacts contacts = new Contacts();
		contactMapper.mapContactDTOToContact(contactDTO, contacts);

		try {
			log.info("Updating contact with email: {}", contactDTO.getEmail());
			contactService.updateContact(contactDTO, imageFile, principal.getName());
			session.setAttribute("message", new Message("Contact Updated Successfully!!", "alert-success"));
			model.addAttribute("contact", new Contacts());
		} catch (Exception e) {
			session.setAttribute("message", new Message("Error: " + e.getMessage(), "alert-danger"));
			model.addAttribute("contact", contacts);
		}
		model.addAttribute("title", "Update Contact");
		return "/normal/edit_contact";
	}

	//Handle delete contact request
	@PostMapping("/contact/delete/{email}")
	public String deleteContact(@PathVariable String email, Principal principal) {
		try {
			log.info("Deleting contact with email: {}", email);
			contactService.deleteContactByEmail(email, principal.getName());
			session.setAttribute("message", new Message("Contact Deleted Successfully!!", "alert-success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Error: " + e.getMessage(), "alert-danger"));
			log.error("Error deleting contact with email {}: {}", email, e.getMessage());
		}
		return "redirect:/userControll/show-contacts";
	}

	//Handle user profile details request
	@GetMapping("/profile")
	public String userProfile(Model model, Principal principal) {
		model.addAttribute("user", userService.findByUsername(principal.getName()));
		return "/normal/ViewUserProfile";
	}


}
