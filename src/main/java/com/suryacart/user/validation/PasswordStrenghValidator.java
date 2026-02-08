package com.suryacart.user.validation;

import java.util.List;

import com.suryacart.user.annotations.PasswordValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrenghValidator implements ConstraintValidator<PasswordValidator, String> {

	List<String> commonPasswords;

	@Override
	public void initialize(PasswordValidator constraintAnnotation) {
		// Initialize a list of common passwords to check against
		commonPasswords = List.of("12345", "password", "12345678", "qwerty", "abc123", "111111", "1234567890");
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		return password != null && password.length() >= 8 && !commonPasswords.contains(password);
	}

}
