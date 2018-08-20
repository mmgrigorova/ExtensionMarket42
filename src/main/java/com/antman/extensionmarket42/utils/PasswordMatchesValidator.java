package com.antman.extensionmarket42.utils;

import com.antman.extensionmarket42.models.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserDto user = (UserDto) value;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
