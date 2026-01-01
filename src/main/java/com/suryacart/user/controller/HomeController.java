package com.suryacart.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.suryacart.user.helper.Message;
import com.suryacart.user.model.dto.UserDTO;
import com.suryacart.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {

	private final UserService userService;

	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("title", "Home controller - Smart Contact Manager");
		return "home";
	}

	@GetMapping("/Signup")
	public String SignUpan(Model model) {
		model.addAttribute("title", "Sign UP here");
		model.addAttribute("userDTO", new UserDTO());
		return "Signup";
	}

	@PostMapping("/do_register")
	public String getRegister(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean check, HttpSession session,
			Model model) {

		try {
			if (!check)
				throw new Exception("You must agree to terms");

			if (result.hasErrors()) {
				model.addAttribute("userDTO", userDTO);
				return "Signup";
			}

			userService.registerUser(userDTO);
			session.setAttribute("message", new Message("Successfully sign up!!", "alert-success"));
			model.addAttribute("userDTO", new UserDTO());

		} catch (Exception e) {
			session.setAttribute("message", new Message("Error: " + e.getMessage(), "alert-danger"));
			model.addAttribute("userDTO", userDTO);
		}

		return "Signup";
	}

	@GetMapping("/signin")
	public String customlogin() {
		return "login";
	}

	@PostMapping("/dologin")
	public String doLogin() {
		log.info("Login successful");
		return "login";
	}
}
