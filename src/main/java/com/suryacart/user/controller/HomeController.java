package com.suryacart.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.suryacart.user.helper.Message;
import com.suryacart.user.model.User;
import com.suryacart.user.repository.UserRepositoryImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepositoryImpl userRepositoryImpl;

	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("title", "Home controller - Smart Contact Manager");
		return "home";
	}

	@GetMapping("/Signup")
	public String SignUpan(Model model) {
		model.addAttribute("title", "Sign UP here");
		model.addAttribute("user", new User());
		return "Signup";
	}

	@PostMapping("/do_register")
	public String getRegister(@Valid @ModelAttribute("user") User user, BindingResult result1, Model model,
			@RequestParam(value = "agreement", defaultValue = "false") boolean check, HttpSession session)
			throws Exception {

		System.out.println(user);
		System.out.println(check);

		try {
			if (!check) {
				System.out.println("You have not agree term and conditions");
				throw new Exception("You have not agree term and conditions");
			}

			if (result1.hasErrors()) {
				System.out.println("Error: " + result1.toString());
				model.addAttribute("user", user);
				return "Signup";
			}

			user.setImage("This is image");
			user.setIsEnable("true");
			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			User newUser = userRepositoryImpl.save(user);
			System.out.println(newUser);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully sign up!!", "alert-success"));

		} catch (Throwable e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Some thing went wrong!!" + e.getMessage(), "alert-danger"));
			model.addAttribute("user", user);
		}

		return "Signup";
	}

	@GetMapping("/signin")
	public String customlogin() {
		return "login";
	}

	@GetMapping("/logout")
	public String logOut() {
		return "home";
	}

}
