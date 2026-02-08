package com.suryacart.user.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.suryacart.user.validation.PasswordStrenghValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Documented
@Constraint(validatedBy = PasswordStrenghValidator.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface PasswordValidator {

	String message() default "Password must be strong";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
