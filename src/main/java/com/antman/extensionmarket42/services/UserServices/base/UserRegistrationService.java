package com.antman.extensionmarket42.services.UserServices.base;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserDto;
import com.antman.extensionmarket42.utils.exceptions.EmailExistsException;

public interface UserRegistrationService {
    User registerNewUserAccount(UserDto userDto) throws EmailExistsException;
}
