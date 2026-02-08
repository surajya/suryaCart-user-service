package com.suryacart.user.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.suryacart.user.validation.FieldValueMatchValidation;

import jakarta.validation.Constraint;


@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = FieldValueMatchValidation.class)
public @interface FieldsValueMatch {

	String message() default "Fields do not match";

	String field();

	String fieldMatch();

	Class<?>[] groups() default {};

	Class<? extends jakarta.validation.Payload>[] payload() default {};

	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {

		FieldsValueMatch[] value();
	}
}
