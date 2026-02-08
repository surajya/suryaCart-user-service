package com.suryacart.user.validation;

import org.springframework.beans.BeanWrapperImpl;

import com.suryacart.user.annotations.FieldsValueMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldValueMatchValidation implements ConstraintValidator<FieldsValueMatch, Object> {

	private String field;
	private String fieldMatch;

	@Override
	public void initialize(FieldsValueMatch constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.fieldMatch = constraintAnnotation.fieldMatch();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
			Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
			return fieldValue != null && fieldValue.equals(fieldMatchValue);
		} catch (Exception e) {
			return false;
		}
	}

}
