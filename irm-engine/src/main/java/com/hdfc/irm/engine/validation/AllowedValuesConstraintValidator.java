package com.hdfc.irm.engine.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowedValuesConstraintValidator implements ConstraintValidator<AllowedValues, String> {

	String[] allowedStringValues;

	@Override
	public void initialize(AllowedValues allowedValues) {
		this.allowedStringValues = allowedValues.value();
	}

	@Override
	public boolean isValid(String theValue, ConstraintValidatorContext arg1) {
		boolean result = false;

		if (theValue != null) {
			for (String value : allowedStringValues) {

				result = value.equals(theValue);
				if (result) {
					break;
				}
			}
		}

		return result;
	}

}
