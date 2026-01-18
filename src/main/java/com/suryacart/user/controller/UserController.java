package com.suryacart.user.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.service.ContactService;
import com.suryacart.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/userControll")
public class UserController {

	private final UserService userService;
	private final ContactService contactService;

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
		return "/normal/add_contact_form";
	}

	@PostMapping("/process-contact")
	public String processContact(@Valid @ModelAttribute("contactDTO") ContactDTO contactDTO,
			@RequestParam("image") MultipartFile imageFile,
			Principal principal) {
		contactService.addContactToUser(contactDTO, imageFile, principal.getName());
		return "/normal/add_contact_form";
	}
}
