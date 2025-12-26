package com.suryacart.user.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suryacart.user.model.entity.Contacts;
import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.UserRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

@RequestMapping("/userControll")
public class UserController {

	UserRepositoryImpl userRepositoryImpl;

	UserController(UserRepositoryImpl uri) {
		userRepositoryImpl = uri;
	}

	// method for common data binding
	@ModelAttribute
	public void getCommonData(Model model, Principal principal) {
		String name = principal.getName();
		User userName = userRepositoryImpl.getUserByUserName(name);
		// System.out.println(userName);
		model.addAttribute("user", userName);
	}

	// UserDashboard home
	@GetMapping("/index")
	public String userDashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard");
		return "/normal/User_DashBoard";
	}

	// open add-form handler
	@GetMapping("/addContact")
	public String openAddContactForm(Model model, Principal principal) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("addContact", new Contacts());
		return "/normal/add_contact_form";
	}

	// Handle process of contact details
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute("addContact") Contacts contacts, Principal principal) {

		User user = userRepositoryImpl.getUserByUserName(principal.getName());
		contacts.setUser(user);
		user.getContacts().add(contacts);
		userRepositoryImpl.save(user);
		log.info("information about user:{}", user);
		log.info("information about contact:{}", contacts);
		return "/normal/add_contact_form";
	}

}
